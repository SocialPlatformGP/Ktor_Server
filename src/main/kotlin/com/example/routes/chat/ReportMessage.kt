package com.example.routes.chat

import com.example.data.requests.ChatRequest
import com.example.data.requests.PostRequest
import com.example.repository.MessageDataSource
import com.example.repository.PostRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.reportMessage(
    messageSource: MessageDataSource
) {
    post(EndPoint.Post.ReportPost.route) {
        val request = call.receiveNullable<ChatRequest.ReportMessage>() ?: return@post call.respond(
            HttpStatusCode.BadRequest,
            "مش عارف استقبل الjson"
        )
        val wasAcknowledged = messageSource.reportMessage(request)
        if (!wasAcknowledged) {
            return@post call.respond(HttpStatusCode.Conflict, message = "Error Reporting the message")
        }
        call.respond(HttpStatusCode.OK, message = "Message Reported successfully")
    }
}