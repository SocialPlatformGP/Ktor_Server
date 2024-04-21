package com.example.repository

import com.example.data.models.chat.*
import com.example.data.models.user.User
import com.example.data.requests.ChatRequest
import com.example.data.responses.ChatResponse
import com.example.utils.Constants.BASE_URL
import com.example.utils.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.litote.kmongo.addToSet
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.`in`
import org.litote.kmongo.setValue
import java.io.File
import java.net.URLConnection
import java.util.*
import javax.activation.MimeType

class MessageDataSourceImpl(
    db: CoroutineDatabase
) : MessageDataSource {
    private val messages = db.getCollection<Message>()
    private val rooms = db.getCollection<Room>()
    private val recentRoom = db.getCollection<RecentRoom>()
    private val userRooms = db.getCollection<UserRooms>()
    private val user = db.getCollection<User>()
    override suspend fun checkRoomExists(request: ChatRequest.RoomExistRequest): Room? {
        val receiver = user.findOne(User::id eq request.receiverId) ?: return null
        val senderRooms = userRooms.findOne(UserRooms::userId eq request.senderId) ?: return createPrivateRoom(
            request.senderId,
            request.receiverId
        )
        val receiverRooms = userRooms.findOne(UserRooms::userId eq request.receiverId) ?: return createPrivateRoom(
            request.senderId,
            request.receiverId
        )
        senderRooms.recentRooms.intersect(receiverRooms.recentRooms.toSet()).let {
            if (it.isNotEmpty()) {
                val room = rooms.findOne(Room::id eq it.first())
                if (room != null && room.isPrivate) {
                    return room.copy(
                        name = receiver.firstName + " " + receiver.lastName,
                        picUrl = receiver.profilePictureURL ?: ""
                    )
                } else {
                    return createPrivateRoom(request.senderId, request.receiverId)
                }
            }
        }

        return null
    }

    private suspend fun createPrivateRoom(sender: String, receiver: String): Room {
        val usersId = mutableListOf(sender, receiver)
        val receiverUser = user.findOne(User::id eq receiver)
        val room = Room(
            name = "",
            picUrl = "",
            isPrivate = true,
            members = usersId.associateWith { false },
            bio = "",
        )
        rooms.insertOne(room).wasAcknowledged().let { if (!it) println("Room not created") }
        room.newRecentRoom().let {
            recentRoom.insertOne(it).wasAcknowledged().let { if (!it) println("RecentRoom not created") }
        }
        usersId.forEach {
            val userRoomsExist = userRooms.findOne(UserRooms::userId eq it)
            if (userRoomsExist == null) {
                UserRooms(
                    userId = it,
                    recentRooms = listOf(room.id)
                ).let { newUserRoom -> userRooms.insertOne(newUserRoom) }
            } else {
                userRooms.updateOne(UserRooms::userId eq it, addToSet(UserRooms::recentRooms, room.id))
                    .wasAcknowledged().let { if (!it) println("UserRooms not updated") }
            }
        }

        return room.copy(
            name = receiverUser?.firstName + " " + receiverUser?.lastName,
            picUrl = receiverUser?.profilePictureURL ?: ""
        )
    }

    override suspend fun createGroupRoom(request: ChatRequest.CreateGroupRoom): Room {
        val usersId = request.userIds.toMutableList()
        usersId.add(request.creatorId)
        val room = Room(
            name = request.groupName,
            picUrl = "",
            isPrivate = false,
            members = usersId.associateWith {
                if(it == request.creatorId) true else false
            },
            bio = "",
        )

        val folder = File("files/rooms/${room.id}")
        room.picUrl = if (request.groupAvatar.isNotEmpty()) {
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val file_id = UUID.randomUUID().toString()
            val mimeType = withContext(Dispatchers.IO) {
                URLConnection.guessContentTypeFromStream(request.groupAvatar.inputStream())
            }
            val extension = MimeType(mimeType).subType
            FileUtils.saveByteArrayToFile(request.groupAvatar, "files/rooms/${room.id}/" + file_id + "." + extension)
            "$BASE_URL/rooms/${room.id}/$file_id.$extension"

        } else ""
        rooms.insertOne(room).wasAcknowledged().let { if (!it) println("Room not created") }
        room.newRecentRoom().let {
            recentRoom.insertOne(it).wasAcknowledged().let { if (!it) println("RecentRoom not created") }
        }
        usersId.forEach {
            val userRoomsExist = userRooms.findOne(UserRooms::userId eq it)
            if (userRoomsExist == null) {
                UserRooms(
                    userId = it,
                    recentRooms = listOf(room.id)
                ).let { newUserRoom -> userRooms.insertOne(newUserRoom) }
            } else {
                userRooms.updateOne(UserRooms::userId eq it, addToSet(UserRooms::recentRooms, room.id))
                    .wasAcknowledged().let { if (!it) println("UserRooms not updated") }
            }
        }

        return room

    }

    override suspend fun getRoom(roomId: String): Room {
        println("RoomId: $roomId")
        return rooms.findOne(Room::id eq roomId) ?: throw Exception("Room not found")
    }

    override suspend fun addMessageToRoom(message: Message) {
        messages.insertOne(message)
        val recent = recentRoom.findOne(RecentRoom::roomId eq message.roomId)
//        recentRoom.updateOne(
//            RecentRoom::roomId eq message.roomId,
//            setValue(RecentRoom::lastMessage, message.content)
//        )
//        recentRoom.updateOne(
//            RecentRoom::roomId eq message.roomId,
//            setValue(RecentRoom::lastMessageTime, message.createdAt)
//        )
//        recentRoom.updateOne(
//            RecentRoom::roomId eq message.roomId,
//            setValue(RecentRoom::sender_id, message.senderId)
//        )
        recentRoom.replaceOne(
            RecentRoom::roomId eq message.roomId,
            recent?.copy(
                lastMessage = message.content,
                lastMessageTime = message.createdAt,
                sender_id = message.senderId,
                isPrivate = recent.isPrivate,
                title = recent.title,
            ) ?: RecentRoom(
                roomId = message.roomId,
                lastMessage = message.content,
                lastMessageTime = message.createdAt,
                sender_id = message.senderId,
                isPrivate = false,
                title = "",
            )
        )
    }

    override suspend fun getMessagesFromRoom(roomId: String): List<ChatResponse.MessageResponse> {
        return messages.find(Message::roomId eq roomId).descendingSort(Message::createdAt).toList().map {
            val user = user.findOne(User::id eq it.senderId)
            it.toResponse(user?.firstName + " " + user?.lastName, user?.profilePictureURL ?: "")
        }
    }

    override suspend fun getRecentRooms(request: ChatRequest.GetAllRecentRooms): ChatResponse.GetAllRecentRooms {
        val userRooms = userRooms.find(UserRooms::userId eq request.userId).toList()
        val recents = recentRoom.find(RecentRoom::roomId `in` userRooms.flatMap { it.recentRooms }).toList()
        val rooms = rooms.find(Room::id `in` recents.map { it.roomId }).toList()

        val result = recents.map { recent ->
            val room = rooms.find { it.id == recent.roomId } ?: return@map null
            if (room.isPrivate) {
                val test = user.findOne(User::id eq request.userId)
                println("\n\n\n\n from ${test?.firstName} \n\n\n\n")
                val userid = room.members.keys.filter { it != request.userId }.firstOrNull()
                val user = user.findOne(User::id eq userid)
                println("\n\n\n\nto ${user?.firstName} \n\n\n\n")
                return@map RecentRoomResponse(
                    roomId = room.id,
                    title = user?.firstName + " " + user?.lastName,
                    lastMessage = recent.lastMessage,
                    lastMessageTime = recent.lastMessageTime,
                    senderName = "",
                    isPrivate = true,
                    pic_url = user?.profilePictureURL ?: ""
                )
            } else {
                val sender = user.findOne(User::id eq recent.sender_id)
                val senderName = if(sender?.id == request.userId) "You" else sender?.firstName + " " + sender?.lastName
                return@map RecentRoomResponse(
                    roomId = room.id,
                    title = room.name,
                    lastMessage = recent.lastMessage,
                    lastMessageTime = recent.lastMessageTime,
                    senderName = if(sender != null) senderName else "",
                    isPrivate = false,
                    pic_url = room.picUrl
                )
            }
        }.filterNotNull().filterNot { it.lastMessage == "###new###" && it.isPrivate}

        return ChatResponse.GetAllRecentRooms(result)

    }

    override suspend fun updateRoomAvatar(request: ChatRequest.UpdateRoomAvatar): String {
        val room = rooms.findOne(Room::id eq request.roomId) ?: throw Exception("Room not found")
        val folder = File("files/rooms/${room.id}")
        room.picUrl = if (request.byteArray.isNotEmpty()) {
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val file_id = UUID.randomUUID().toString()
            val mimeType = withContext(Dispatchers.IO) {
                URLConnection.guessContentTypeFromStream(request.byteArray.inputStream())
            }
            val extension = MimeType(mimeType).subType
            FileUtils.saveByteArrayToFile(request.byteArray, "files/rooms/${room.id}/" + file_id + "." + extension)
            "$BASE_URL/rooms/${room.id}/$file_id.$extension"

        } else ""
        rooms.updateOne(Room::id eq request.roomId, room.copy(picUrl = room.picUrl))
        val recent = recentRoom.findOne(RecentRoom::roomId eq request.roomId) ?: throw Exception("RecentRoom not found")
        recentRoom.updateOne(RecentRoom::roomId eq request.roomId, recent.copy(pic_url = room.picUrl))
        return room.picUrl
    }

    override suspend fun updateRoomName(request: ChatRequest.UpdateRoomName): Boolean {
        val room = rooms.findOne(Room::id eq request.roomId) ?: throw Exception("Room not found")
        return rooms.updateOne(Room::id eq request.roomId, room.copy(name = request.name)).wasAcknowledged()
    }

    override suspend fun addMembers(request: ChatRequest.AddMembers): Boolean {
        val room = rooms.findOne(Room::id eq request.roomId) ?: throw Exception("Room not found")
        val newMembers = request.userIds.filter { !room.members.containsKey(it) }.associateWith { false }.toMap()
        val oldMembers = room.members.toMutableMap()
        val updatedMembers = oldMembers.plus(newMembers)

        request.userIds.forEach {
            val userRoomsExist = userRooms.findOne(UserRooms::userId eq it)
            if (userRoomsExist == null) {
                UserRooms(
                    userId = it,
                    recentRooms = listOf(room.id)
                ).let { newUserRoom -> userRooms.insertOne(newUserRoom) }
            } else {
                userRooms.updateOne(UserRooms::userId eq it, addToSet(UserRooms::recentRooms, room.id))
                    .wasAcknowledged().let { if (!it) println("UserRooms not updated") }
            }
        }
        return rooms.updateOne(Room::id eq request.roomId, room.copy(members = updatedMembers)).wasAcknowledged()

    }

    override suspend fun removeMember(request: ChatRequest.RemoveMember): Boolean {
        val room = rooms.findOne(Room::id eq request.roomId) ?: throw Exception("Room not found")
        val updatedMembers = room.members.toMutableMap()
        updatedMembers.remove(request.userId)
        val userRoomsExist = userRooms.findOne(UserRooms::userId eq request.userId)
        if (userRoomsExist != null) {
            userRooms.updateOne(UserRooms::userId eq request.userId, setValue(UserRooms::recentRooms, userRoomsExist.recentRooms.filter { it != room.id }))
                .wasAcknowledged().let { if (!it) println("UserRooms not updated") }
        }
        return rooms.updateOne(Room::id eq request.roomId, room.copy(members = updatedMembers)).wasAcknowledged()
    }

    suspend fun createRoom(sender: String, receiver: String): Room {
        val room = Room(
            name = "",
            picUrl = "",
            isPrivate = true,
            members = mapOf(sender to false, receiver to false),
            bio = "",
        )
        UserRooms(
            userId = sender,
            recentRooms = listOf(room.id)
        ).let { userRooms.insertOne(it) }
        room.newRecentRoom().let {
            recentRoom.insertOne(it)
        }
        rooms.insertOne(room)
        return room
    }


}