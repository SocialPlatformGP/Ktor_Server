package com.example.routes.reply

import com.example.data.requests.ReplyRequest
import com.example.repository.ReplyRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createReply(
    replyRepository: ReplyRepository
) {
    post(EndPoint.Reply.CreateReply.route) {
        val request = call.receiveNullable<ReplyRequest.CreateRequest>() ?: return@post call.respond(HttpStatusCode.BadRequest, message = "Can't receive the json")
        
        val fieldsBlank = request.reply.content.isBlank()

        if (fieldsBlank) {
            return@post call.respond(HttpStatusCode.Conflict, message = "Fields required")
            
        }
        val wasAcknowledged = replyRepository.createReply(request.reply.toResponse())
        if (!wasAcknowledged) {
            return@post call.respond(HttpStatusCode.Conflict, message = "Error Creating the reply")
            
        }
        call.respond(HttpStatusCode.OK, message = "Reply created successfully")

    }
}


