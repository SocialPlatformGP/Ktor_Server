package com.example.routes.post

import com.example.data.requests.AddTagRequest
import com.example.repository.PostRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.postTags(
    postRepository: PostRepository
) {
    post(EndPoint.Post.Tags.InsertTag.route) {
        val request = call.receiveNullable<AddTagRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")
            return@post
        }
        val wasAcknowledged = postRepository.insertTag(request.toEntity())
        call.respond(HttpStatusCode.OK, wasAcknowledged)

    }
    get(EndPoint.Post.Tags.GetAllTags.route) {
        val response = postRepository.getTags()
        call.respond(HttpStatusCode.OK, response)

    }
}



