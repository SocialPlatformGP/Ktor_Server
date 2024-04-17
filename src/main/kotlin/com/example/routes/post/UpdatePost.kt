package com.example.routes.post

import com.example.data.responses.PostResponse
import com.example.repository.PostRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updatePost(
    postRepository: PostRepository
) {
    put(EndPoint.Post.UpdatePost.route) {
        val request = call.receiveNullable<PostResponse>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")
            return@put
        }
        val wasAcknowledged = postRepository.updatePost(request)
        call.respond(HttpStatusCode.OK, wasAcknowledged)
    }
}