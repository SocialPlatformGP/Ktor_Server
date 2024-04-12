package com.example.routes

import com.example.data.models.Message
import com.example.data.models.Room
import com.example.data.requests.ChatRequest
import com.example.data.responses.ChatResponse
import com.example.plugins.ChatSession
import com.example.repository.MessageDataSource
import com.example.room.MemberAlreadyExistsException
import com.example.room.RoomController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import java.net.http.WebSocket

fun Route.chatRoute2(
    roomController: RoomController,
    messageDataSource: MessageDataSource
) {
    webSocket("/chat-socket") {
        val session = call.sessions.get<ChatSession>()
        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return@webSocket
        }
        try {
            roomController.onJoin(
                session.userName,
                session.sessionId,
                this
            )
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    val receivedText = frame.readText()
                    println("Received Text: $receivedText")
                    roomController.sendMessage(
                        Message(
                            content = receivedText,
                            roomId = session.roomId,
                            senderId = session.userName
                        )
                    )
                }
            }
        } catch (e: MemberAlreadyExistsException) {
            call.respond(HttpStatusCode.Conflict)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            roomController.removeMember(session.userName)
        }

    }
    post("/isRoomExist") {
        val request = call.receiveNullable<ChatRequest.CheckRoomExists>() ?:return@post call.respond(HttpStatusCode.BadRequest)
        val isRoomExist = messageDataSource.checkRoomExists(request)
        println("Is Room Exist: $isRoomExist")
        val response = ChatResponse.CheckRoomExists(isRoomExist)
        println("Response: $response")
        call.respond(HttpStatusCode.OK, response)
    }
    post ("/getMessages"){

        println("********Get Messages")
        val request = call.receiveNullable<ChatRequest.GetMessagesFromRoom>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        println("Request: $request")
        val messages = messageDataSource.getMessagesFromRoom(request.roomId)
        println("Messages: $messages")
        val response = ChatResponse.GetMessagesFromRoom(messages)
        println("Response: $response")
        call.respond(HttpStatusCode.OK, response)
    }
}

