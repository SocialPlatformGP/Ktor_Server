package com.example.routes

import com.example.data.models.Message
import com.example.data.models.Tag
import com.example.data.requests.ChatRequest
import com.example.data.responses.ChatResponse
import com.example.data.responses.TagResponse
import com.example.plugins.ChatSession
import com.example.repository.MessageDataSource
import com.example.room.Member
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

fun Route.chatRoute2(
    roomController: RoomController,
    messageDataSource: MessageDataSource
) {
    webSocket("/chatSocket") {
        val session = call.sessions.get<ChatSession>()
        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return@webSocket
        }
        println("Session: ${session.userId} ${session.sessionId} ${session.roomId}")
        try {
            roomController.onJoin(
                userId = session.userId,
                sessionId = session.sessionId,
                roomId = session.roomId,
                socket = this
            )
            incoming.consumeEach {
                if(it is Frame.Text) {
                    val receivedText = Json.decodeFromString<ChatRequest.SendMessage>(it.readText())
                    roomController.sendMessage(receivedText)
                    println("\n\n\n\n Message: $receivedText \n\n\n\n")
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


    post("/isRoomExist") {
        val request =
            call.receiveNullable<ChatRequest.RoomExistRequest>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        val isRoomExist = messageDataSource.checkRoomExists(request)
        println("Is Room Exist: $isRoomExist")
        val response = ChatResponse.RoomExistResponse(isRoomExist)
        println("Response: $response")
        call.respond(HttpStatusCode.OK, response)
    }
    post("/getMessages") {

        println("********Get Messages")
        val request = call.receiveNullable<ChatRequest.GetMessagesFromRoom>()
            ?: return@post call.respond(HttpStatusCode.BadRequest)
        println("Request: $request")
        val messages = messageDataSource.getMessagesFromRoom(request.roomId)
        println("Messages: $messages")
        val response = ChatResponse.GetMessagesFromRoom(messages)
        println("Response: $response")
        call.respond(HttpStatusCode.OK, response)
    }
    post("fetchChatMessages") {
        val request = call.receiveNullable<ChatRequest.FetchMessages>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        val messages = messageDataSource.getMessagesFromRoom(request.roomId)
        call.respond(HttpStatusCode.OK, messages)
    }
}

fun Route.RecentRoomsRoute(
    messageDataSource: MessageDataSource
) {
    post("/getRecentRooms") {
        val request =
            call.receiveNullable<ChatRequest.GetAllRecentRooms>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        println("\n Request: $request \n")
        val recentRooms = messageDataSource.getRecentRooms(request)
        println("\n Recent Rooms: $recentRooms \n")
        call.respond(HttpStatusCode.OK, recentRooms)
    }
}

fun Route.RoomRoute(
    messageDataSource: MessageDataSource
) {
    post("/createGroupRoom") {
        val request =
            call.receiveNullable<ChatRequest.CreateGroupRoom>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        println("\n Request: $request \n")
        val roomId = messageDataSource.createGroupRoom(request)
        println("\n RoomId: $roomId \n")
        call.respond(HttpStatusCode.OK, ChatResponse.CreateGroupRoom(roomId))
    }
}

@Serializable
data class Messego(
    val message : String,
    val senderId : String,
)