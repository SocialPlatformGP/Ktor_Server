package com.example.routes.post

import com.example.data.models.post.LastUpdated
import com.example.repository.post.PostRepository
import com.example.utils.EndPoint
import com.example.utils.PostError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getNewPosts(
    postRepository: PostRepository
) {
    post(EndPoint.Post.GetNewPosts.route) {
        val request = call.receiveNullable<LastUpdated>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, PostError.SERVER_ERROR)
            return@post
        }
        val response = postRepository.getNewPosts(request.lastUpdated)
        println(request.lastUpdated.toString() + "$response")
        call.respond(HttpStatusCode.OK, response)

    }
}