package com.example.routes.reply

import com.example.data.requests.ReplyRequest
import com.example.repository.ReplyRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.downvoteReply(
    replyRepository: ReplyRepository
) {
    post(EndPoint.Reply.DownvoteReply.route) {
        val request = call.receiveNullable<ReplyRequest.DownvoteRequest>() ?:return@post
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")


        val wasAcknowledged = replyRepository.downvoteReply(request)
        if (!wasAcknowledged) {
            return@post call.respond(HttpStatusCode.Conflict, message = "Error Downvoting the reply")

        }
        call.respond(HttpStatusCode.OK, message = "Reply downvoted successfully")
    }
}