package com.example.repository

import com.example.data.models.*
import com.example.data.requests.ChatRequest
import com.example.data.responses.MessageResponse
import org.litote.kmongo.addToSet
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.insertOne
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class MessageDataSourceImpl(
    private val db: CoroutineDatabase
) : MessageDataSource {
    private val messages = db.getCollection<Message>()
    private val rooms = db.getCollection<Room>()
    private val recentRoom = db.getCollection<RecentRoom>()
    private val userRooms = db.getCollection<UserRooms>()
    private val user = db.getCollection<User>()
    override suspend fun checkRoomExists(request: ChatRequest.CheckRoomExists): Room {
        val userRooms = userRooms.findOne(UserRooms::userId eq request.sender)
        println("Rooms: $userRooms")
        if(userRooms == null) return createRoom(request.sender, request.receiver)
        userRooms.recentRooms.forEach {roomId->
            val room = rooms.findOne(Room::id eq roomId)
            println("Room: $room")
            if(room != null){
                if(room.members.containsKey(request.receiver)&& room.isPrivate){
                    return room
                }
            }
        }
        return createRoom(request.sender, request.receiver)
    }

    override suspend fun getRoom(roomId: String): Room {
        println("RoomId: $roomId")
        return rooms.findOne(Room::id eq roomId)?:throw Exception("Room not found")
    }

    override suspend fun addMessageToRoom(message: Message) {
        messages.insertOne(message)
        recentRoom.updateOne(RecentRoom::roomId eq message.roomId, setValue(RecentRoom::lastMessage, message.content))
        recentRoom.updateOne(RecentRoom::roomId eq message.roomId, setValue(RecentRoom::lastMessageTime, message.createdAt))
    }

    override suspend fun getMessagesFromRoom(roomId: String): List<MessageResponse> {
        return messages.find(Message::roomId eq roomId).descendingSort(Message::createdAt).toList().map {
            val user = user.findOne(User::id eq it.senderId)
            it.toResponse(user?.firstName + " " + user?.lastName, user?.profilePictureURL ?: "")
        }
    }

    suspend fun createRoom(sender: String, receiver: String): Room {
        val room = Room(
            name ="",
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