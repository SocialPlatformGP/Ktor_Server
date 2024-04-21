package com.example.routes.chat

import com.example.data.requests.ChatRequest
import com.example.repository.MessageDataSource
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.fetchChatMessages(
   messageDataSource: MessageDataSource
){
    post(EndPoint.Chat.Messages.FetchChatMessages.route) {
        val request = call.receiveNullable<ChatRequest.FetchMessages>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        val messages = messageDataSource.getMessagesFromRoom(request.roomId)
        call.respond(HttpStatusCode.OK, messages)
    }
}