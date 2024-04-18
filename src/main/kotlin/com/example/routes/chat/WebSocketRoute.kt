package com.example.routes.chat

import com.example.data.requests.ChatRequest
import com.example.plugins.ChatSession
import com.example.room.MemberAlreadyExistsException
import com.example.room.RoomController
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.Json

fun Route.webSocketRoute(
    roomController: RoomController,
) {
    webSocket(EndPoint.Chat.WebSocket.ChatSocket.route) {
        val session = call.sessions.get<ChatSession>()
        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return@webSocket
        }
        println("Session: ${session.userId} ${session.sessionId} ")
        try {
            roomController.onJoin(
                userId = session.userId, sessionId = session.sessionId, socket = this
            )
            incoming.consumeEach {
                if (it is Frame.Text) {
                    val receivedText = Json.decodeFromString<ChatRequest.SendMessage>(it.readText())
                    roomController.sendMessage(receivedText)
                    println("\n\n\n\n Message: ${receivedText.content} \n\n\n\n")
                }
            }


        } catch (e: MemberAlreadyExistsException) {
            println("\n\n\n Error: $e\n\n\n\n")
            call.respond(HttpStatusCode.Conflict)
        } catch (e: Exception) {
            println("Error: $e")
        } finally {
            roomController.removeMember(session.userId)
        }

    }

}