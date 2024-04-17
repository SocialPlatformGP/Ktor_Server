package com.example.room

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
    private val authRepository: AuthRepository
) {
    private val members = ConcurrentHashMap<String, Member>()

    fun onJoin(
        userId: String,
        sessionId: String,
        roomId :String,
        socket: DefaultWebSocketServerSession
    ) {
        if (members.containsKey(userId))
            throw MemberAlreadyExistsException()
        members[userId] = Member(userId, sessionId,roomId, socket)
    }

    suspend fun sendMessage(
        message: ChatRequest.SendMessage
    ) {
        val userData = authRepository.findUserById(message.senderId)
        val messageResponse = message.toResponse(
            senderName = userData?.firstName + " " + userData?.lastName,
            senderPicUrl = userData?.profilePictureURL ?: ""
        )
        val result = messageDataSource.getRoom(message.roomId)
        val keys = result.members.keys
        members.values.filter {
            keys.contains(it.userId)&& it.roomId == result.id
        }.forEach{
            it.socket.outgoing.send(Frame.Text(Json.encodeToString(messageResponse)))
        }
        messageDataSource.addMessageToRoom(message.toMessage())
    }


    suspend fun removeMember(username: String) {
        members[username]?.let { member ->
            member.socket.close()
            members.remove(username)
        }
    }

}