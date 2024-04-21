package com.example.routes.post

import com.example.data.requests.UpdateOrDeletePostRequest
import com.example.repository.PostRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.upvotePost(
    postRepository: PostRepository
) {
    post(EndPoint.Post.UpdatePost.route) {
        val request = call.receiveNullable<UpdateOrDeletePostRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")
            return@post
        }
        val wasAcknowledged = postRepository.upvotePost(request)
        call.respond(HttpStatusCode.OK, wasAcknowledged)

    }
}