package com.example.routes.reply

import com.example.data.requests.ReplyRequest
import com.example.repository.ReplyRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteReply(
    replyRepository: ReplyRepository
) {
    delete(EndPoint.Reply.DeleteReply.route) {
        val request = call.receiveNullable<ReplyRequest.DeleteRequest>() ?:return@delete
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")


        val wasAcknowledged = replyRepository.deleteReply(request)
        if (!wasAcknowledged) {
            return@delete  call.respond(HttpStatusCode.Conflict, message = "Error Deleting the reply")
        }
        call.respond(HttpStatusCode.OK, message = "Reply deleted successfully")
    }
}