package com.example.routes.post

import com.example.data.requests.PostRequest
import com.example.repository.PostRepository
import com.example.utils.DataError
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updatePost(
    postRepository: PostRepository
) {
    post(EndPoint.Post.UpdatePost.route) {
        val request = call.receiveNullable<PostRequest.UpdateRequest>() ?: return@post call.respond(
            HttpStatusCode.BadRequest,
            DataError.Network.BAD_REQUEST
        )

        val wasAcknowledged = postRepository.updatePost(request)
        if (!wasAcknowledged) {
            return@post call.respond(HttpStatusCode.InternalServerError, DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }
}