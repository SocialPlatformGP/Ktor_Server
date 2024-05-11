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

fun Route.reportReply(
    replyRepository: ReplyRepository
) {
    post(EndPoint.Reply.ReportReply.route) {
        val request = call.receiveNullable<ReplyRequest.ReportRequest>() ?: return@post call.respond(
            HttpStatusCode.BadRequest,
            ReplyError.SERVER_ERROR
        )
        val wasAcknowledged = replyRepository.reportReply(request)
        if (!wasAcknowledged) {
            return@post call.respond(HttpStatusCode.Conflict, ReplyError.SERVER_ERROR)
        }
        call.respond(HttpStatusCode.OK, message = "Reply Reported successfully")
    }
}