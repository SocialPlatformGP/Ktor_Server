package com.example.routes.chat

import com.example.data.requests.ChatRequest
import com.example.repository.MessageDataSource
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateRoomName(
    messageDataSource: MessageDataSource
) {
    post(EndPoint.Chat.Room.UpdateRoomName.route) {
        val request = call.receiveNullable<ChatRequest.UpdateRoomName>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        messageDataSource.updateRoomName(request).apply {
            if (this) call.respond(HttpStatusCode.OK)
            else call.respond(HttpStatusCode.BadRequest)
        }
    }
}