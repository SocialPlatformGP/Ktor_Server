package com.example.routes.chat

import com.example.data.requests.ChatRequest
import com.example.data.responses.ChatResponse
import com.example.repository.MessageDataSource
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getRoomDetails(
    messageDataSource: MessageDataSource
) {
    post(EndPoint.Chat.Room.GetRoomDetails.route) {
        val request =
            call.receiveNullable<ChatRequest.GetRoomDetails>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        messageDataSource.getRoom(request.roomId).let { room ->
            call.respond(HttpStatusCode.OK, ChatResponse.GetRoomDetails(room))
        }
    }
}