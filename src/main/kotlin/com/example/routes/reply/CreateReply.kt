package com.example.routes.reply

import com.example.data.requests.ReplyRequest
import com.example.repository.reply.ReplyRepository
import com.example.utils.EndPoint
import com.example.utils.ReplyError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createReply(
    replyRepository: ReplyRepository
) {
    post(EndPoint.Reply.CreateReply.route) {
        val request = call.receiveNullable<ReplyRequest.CreateRequest>() ?: return@post call.respond(HttpStatusCode.BadRequest, ReplyError.SERVER_ERROR)
        
        val fieldsBlank = request.reply.content.isBlank()

        if (fieldsBlank) {
            return@post call.respond(HttpStatusCode.Conflict, ReplyError.SERVER_ERROR)
            
        }
        val wasAcknowledged = replyRepository.createReply(request.reply.toResponse())
        if (!wasAcknowledged) {
            return@post call.respond(HttpStatusCode.Conflict, ReplyError.SERVER_ERROR)
            
        }
        call.respond(HttpStatusCode.OK, message = "Reply created successfully")

    }
}


