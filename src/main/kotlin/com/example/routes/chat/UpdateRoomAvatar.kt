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

fun Route.updateRoomAvatar(
    messageDataSource: MessageDataSource
) {
    post(EndPoint.Chat.Room.UpdateRoomAvatar.route) {
        val request = call.receiveNullable<ChatRequest.UpdateRoomAvatar>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        messageDataSource.updateRoomAvatar(request).let { url->
            call.respond(HttpStatusCode.OK, ChatResponse.UpdateRoomAvatar(url))
        }
    }
}