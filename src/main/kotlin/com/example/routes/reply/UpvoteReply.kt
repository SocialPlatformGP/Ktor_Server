package com.example.routes.reply

import com.example.data.requests.ReplyRequest
import com.example.repository.ReplyRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.upvoteReply(
    replyRepository: ReplyRepository
) {
    put(EndPoint.Reply.UpvoteReply.route) {
        val request = call.receiveNullable<ReplyRequest.UpvoteRequest>() ?:return@put
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")

        val wasAcknowledged = replyRepository.upvoteReply(request)
        if (!wasAcknowledged) {
            return@put call.respond(HttpStatusCode.Conflict, message = "Error Upvoting the reply")
        }
        call.respond(HttpStatusCode.OK, message = "Reply upvoted successfully")

    }

}