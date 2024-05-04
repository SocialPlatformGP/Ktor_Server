package com.example.routes.post

import com.example.data.requests.PostRequest
import com.example.data.requests.ReplyRequest
import com.example.repository.PostRepository
import com.example.repository.ReplyRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.reportPost(
    postRepository: PostRepository
) {
    post(EndPoint.Chat.Messages.ReportMessage.route) {
        val request = call.receiveNullable<PostRequest.ReportRequest>() ?: return@post call.respond(
            HttpStatusCode.BadRequest,
            "مش عارف استقبل الjson"
        )
        val wasAcknowledged = postRepository.reportPost(request)
        if (!wasAcknowledged) {
            return@post call.respond(HttpStatusCode.Conflict, message = "Error Reporting the post")
        }
        call.respond(HttpStatusCode.OK, message = "Post Reported successfully")
    }
}