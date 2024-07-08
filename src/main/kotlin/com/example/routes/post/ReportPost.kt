package com.example.routes.post

import com.example.data.requests.PostRequest
import com.example.repository.post.PostRepository
import com.example.utils.PostError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.reportPost(
    postRepository: PostRepository
) {
    post("reportPost") {
        val request = call.receiveNullable<PostRequest.ReportRequest>() ?: return@post call.respond(
            HttpStatusCode.BadRequest,
            PostError.SERVER_ERROR
        )
        println("reportPost called: $request")
        postRepository.reportPost(request)
        call.respond(HttpStatusCode.OK, "we will review the post and take necessary actions.")
    }
}