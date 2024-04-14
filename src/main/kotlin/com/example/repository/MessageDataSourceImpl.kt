package com.example.repository

import com.example.data.models.*
import com.example.data.requests.ChatRequest
import com.example.data.responses.ChatResponse
import com.example.utils.FileUtils
import org.litote.kmongo.addToSet
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.`in`
import org.litote.kmongo.setValue
import java.io.File
import java.util.UUID

class MessageDataSourceImpl(
    db: CoroutineDatabase
) : MessageDataSource {
    private val messages = db.getCollection<Message>()
    private val rooms = db.getCollection<Room>()
    private val recentRoom = db.getCollection<RecentRoom>()
    private val userRooms = db.getCollection<UserRooms>()
    private val user = db.getCollection<User>()
    override suspend fun checkRoomExists(request: ChatRequest.RoomExistRequest): Room? {
        val userRooms = userRooms.findOne(UserRooms::userId eq request.senderId) ?: return null
        println("Rooms: $userRooms")
        userRooms.recentRooms.forEach { roomId ->
            val room = rooms.findOne(Room::id eq roomId) ?: return null
            println("Room: $room")
            if (room.members.containsKey(request.receiverId) && room.isPrivate) {
                return room
            }
        }
        return null
    }

    override suspend fun createGroupRoom(request: ChatRequest.CreateGroupRoom): String {
        val room = Room(
            name = request.groupName,
            picUrl = "",
            isPrivate = false,
            members = request.userIds.map {
                if(it ==request.creatorId) it to true else it to false
            }.toMap(),
            bio = "",
        )
        val folder = File("files/rooms/${room.id}")
        room.picUrl = if (request.groupAvatar.isNotEmpty()) {
            if (!folder.exists()) {
                folder.mkdirs()
            }
            FileUtils.saveByteArrayToFile(request.groupAvatar, "files/rooms/${room.id}/" + UUID.randomUUID().toString()).path

        } else ""
        rooms.insertOne(room).wasAcknowledged().let { if (!it) println("Room not created") }
        room.newRecentRoom().let {
            recentRoom.insertOne(it).wasAcknowledged().let { if (!it) println("RecentRoom not created") }
        }
        userRooms.updateOne(UserRooms::userId eq request.creatorId, addToSet(UserRooms::recentRooms, room.id)).wasAcknowledged().let { if (!it) println("UserRooms not updated") }
        return room.id

    }

    override suspend fun getRoom(roomId: String): Room {
        println("RoomId: $roomId")
        return rooms.findOne(Room::id eq roomId) ?: throw Exception("Room not found")
    }

    override suspend fun addMessageToRoom(message: Message) {
        messages.insertOne(message)
        recentRoom.updateOne(RecentRoom::roomId eq message.roomId, setValue(RecentRoom::lastMessage, message.content))
        recentRoom.updateOne(
            RecentRoom::roomId eq message.roomId,
            setValue(RecentRoom::lastMessageTime, message.createdAt)
        )
    }

    override suspend fun getMessagesFromRoom(roomId: String): List<ChatResponse.MessageResponse> {
        return messages.find(Message::roomId eq roomId).descendingSort(Message::createdAt).toList().map {
            val user = user.findOne(User::id eq it.senderId)
            it.toResponse(user?.firstName + " " + user?.lastName, user?.profilePictureURL ?: "")
        }
    }

    override suspend fun getRecentRooms(request: ChatRequest.GetAllRecentRooms): ChatResponse.GetAllRecentRooms {
        val userRooms = userRooms.findOne(UserRooms::userId eq request.userId) ?: throw Exception("User not found")
        println("\n UserRooms: $userRooms \n")
        val recentRooms = recentRoom.find(RecentRoom::roomId `in` userRooms.recentRooms).toList()
        println("\n RecentRooms: $recentRooms \n")
        val result = recentRooms.map { recent_room ->
            val sender = user.findOne(User::id eq recent_room.sender_id)
            val userName = sender?.firstName + " " + sender?.lastName
            if (recent_room.isPrivate) {
                if (recent_room.sender_id == request.userId) {
                    val receiver = user.findOne(User::id eq recent_room.receiver_id)
                    val receiverName = receiver?.firstName + " " + receiver?.lastName
                    recent_room.toResponse(
                        title = receiverName,
                        pic_url = receiver?.profilePictureURL ?: "",
                        senderName = receiverName
                    )
                } else {
                    recent_room.toResponse(
                        title = userName,
                        pic_url = sender?.profilePictureURL ?: "",
                        senderName = userName
                    )
                }

            } else {
                if (recent_room.sender_id == request.userId) {
                    recent_room.toResponse(
                        title = recent_room.title,
                        pic_url = recent_room.pic_url,
                        senderName = "you"
                    )
                } else throw Exception("User not found")
            }
        }

        return ChatResponse.GetAllRecentRooms(result)

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