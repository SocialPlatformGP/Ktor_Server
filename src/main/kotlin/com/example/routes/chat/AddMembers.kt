package com.example.routes.chat

import com.example.data.requests.ChatRequest
import com.example.repository.MessageDataSource
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.addMembers(
    messageDataSource: MessageDataSource
) {
    post(EndPoint.Chat.Room.AddMembers.route) {
        val request =
            call.receiveNullable<ChatRequest.AddMembers>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        messageDataSource.addMembers(request).apply {
            if (this) call.respond(HttpStatusCode.OK)
            else call.respond(HttpStatusCode.BadRequest)
        }
    }
}