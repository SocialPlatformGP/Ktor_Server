package com.example.routes.post

import com.example.repository.PostRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUserPosts(
    postRepository: PostRepository
) {
    post("getUserPosts") {
        val request = call.receiveNullable<String>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        val response = postRepository.getUserPosts(request)
        call.respond(HttpStatusCode.OK, response)
    }
}