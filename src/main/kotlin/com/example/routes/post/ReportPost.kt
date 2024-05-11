package com.example.routes.post

import com.example.data.requests.PostRequest
import com.example.repository.post.PostRepository
import com.example.utils.EndPoint
import com.example.utils.PostError
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
            PostError.SERVER_ERROR
        )
        val wasAcknowledged = postRepository.reportPost(request)
        if (!wasAcknowledged) {
            return@post call.respond(HttpStatusCode.Conflict, PostError.SERVER_ERROR)
        }
        call.respond(HttpStatusCode.OK, message = "Post Reported successfully")
    }
}