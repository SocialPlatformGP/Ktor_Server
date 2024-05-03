package com.example.routes.reply

import com.example.data.requests.ReplyRequest
import com.example.repository.ReplyRepository
import com.example.utils.EndPoint
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
            "مش عارف استقبل الjson"
        )
        val wasAcknowledged = replyRepository.reportReply(request)
        if (!wasAcknowledged) {
            return@post call.respond(HttpStatusCode.Conflict, message = "Error Deleting the reply")
        }
        call.respond(HttpStatusCode.OK, message = "Reply deleted successfully")
    }
}