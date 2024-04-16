package com.example.room

import com.example.data.models.Message
import com.example.data.requests.ChatRequest
import com.example.data.responses.ChatResponse
import com.example.repository.AuthRepository
import com.example.repository.MessageDataSource
import com.example.routes.Messego
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
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
        println("On Join: $userId $sessionId $socket")
        if (members.containsKey(userId)) {
            throw MemberAlreadyExistsException()
        }
        members[userId] = Member(userId, sessionId,roomId, socket)

        println("Members: $members")
    }

    suspend fun sendMessage(
        message: ChatRequest.SendMessage
    ) {
        println("Sending message: $message")
        members.values.forEach {
            println("Sending message to: $it")
            it.socket.outgoing.send(Frame.Text(Json.encodeToString(message)))
        }
//        val userData = authRepository.findUserById(message.senderId)
//        val messageResponse = message.toResponse(
//            senderName = userData?.firstName + " " + userData?.lastName,
//            senderPicUrl = userData?.profilePictureURL ?: ""
//        )
//        println("Message Response: $message")
//        val result = messageDataSource.getRoom(message.roomId)
//        println("Result: $result")
//        val keys = result.members.keys
//        println("Keys: $keys")
//        keys.forEach {
//            val member = members[it]
//            member?.socket?.sendSerialized())
//        }

//        messageDataSource.addMessageToRoom(message)
        //***********
//        members.values.forEach { member: Member ->
//            messageDataSource.addMessageToRoom(message)
//            val parsedMessage = Json.encodeToString(messageEntity)
//            member.socket.send(Frame.Text(parsedMessage))
//        }
    }


    suspend fun removeMember(username: String) {
        members[username]?.let { member ->
            member.socket.close()
            members.remove(username)
        }
    }

}