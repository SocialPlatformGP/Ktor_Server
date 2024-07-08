package com.example.routes.reply

import com.example.data.requests.ReplyRequest
import com.example.repository.reply.ReplyRepository
import com.example.utils.ReplyError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.reportReply(
    replyRepository: ReplyRepository
) {
    post("reportReply") {
        val request = call.receiveNullable<ReplyRequest.ReportRequest>() ?: return@post call.respond(
            HttpStatusCode.BadRequest,
            ReplyError.SERVER_ERROR
        )
        replyRepository.reportReply(request)
        call.respond(HttpStatusCode.OK, message = "we will review the reply and take necessary actions.")
    }
}