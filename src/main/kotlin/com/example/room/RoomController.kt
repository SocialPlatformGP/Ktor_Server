package com.example.room

import com.example.data.models.chat.NewDataResponse
import com.example.data.requests.ChatRequest
import com.example.repository.AuthRepository
import com.example.repository.MessageDataSource
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource,
    private val authRepository: AuthRepository,
) {
    private val members = ConcurrentHashMap<String, Member>()
    private val recentRooms = ConcurrentHashMap<String, Member>()

    fun onJoin(
        userId: String,
        sessionId: String,
        socket: DefaultWebSocketServerSession
    ) {
        if (members.containsKey(userId))
            throw MemberAlreadyExistsException()
        members[userId] = Member(userId, sessionId, socket)
    }
    fun onJoinRecent(
        userId: String,
        sessionId: String,
        socket: DefaultWebSocketServerSession
    ) {
        if (recentRooms.containsKey(userId))
            throw MemberAlreadyExistsException()
        recentRooms[userId] = Member(userId, sessionId, socket)
    }

    suspend fun sendMessage(
        message: ChatRequest.SendMessage
    ) {
        messageDataSource.addMessageToRoom(message.toMessage())
        val userData = authRepository.findUserById(message.senderId)
        val messageResponse = message.toResponse(
            senderName = userData?.name ?: "",
            senderPicUrl = userData?.profilePictureURL ?: ""
        )
        val result = messageDataSource.getRoom(message.roomId)
        val keys = result.members.keys
        members.values.filter {
            keys.contains(it.userId)
        }.forEach{
            val recents = messageDataSource.getRecentRooms(ChatRequest.GetAllRecentRooms(it.userId))
            println("Sending message to ${it.userId} reponse: $messageResponse")
            it.socket.outgoing.send(Frame.Text(Json.encodeToString(NewDataResponse(messageResponse, recents.recentRooms))))
        }
    }



    suspend fun removeMember(username: String) {
        members[username]?.let { member ->
            member.socket.close()
            members.remove(username)
        }
    }

}