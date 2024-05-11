package com.example.routes.post

import com.example.data.requests.PostRequest
import com.example.repository.post.PostRepository
import com.example.utils.EndPoint
import com.example.utils.FileUtils
import com.example.utils.PostError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.util.*

fun Route.createPost(
    postRepository: PostRepository
) {
    post(EndPoint.Post.CreatePost.route) {
        val request = call.receiveNullable<PostRequest.CreateRequest>()?.post ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, PostError.SERVER_ERROR)
            return@post
        }
        val fieldsBlank = request.title.isBlank() || request.body.isBlank()
        println("request: $request")
        if (fieldsBlank) {
            println("fieldsBlank")
            call.respond(HttpStatusCode.Conflict, PostError.SERVER_ERROR)
            return@post
        }

        if (request.attachments.isEmpty()) {
            val wasAcknowledged = postRepository.createPost(postRequest = request.toResponse())
            println("empty attachments: $wasAcknowledged")
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict, message = "Error Creating the post")
                return@post
            }
            call.respond(HttpStatusCode.OK, message = "Post created successfully")
            return@post
        } else {
            val postId = UUID.randomUUID().toString()
            val folder = File("files/${postId}")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val attachments = request.attachments.map {
                val file = FileUtils.saveByteArrayToFile(it.file, "files/${postId}/" + it.name)
                println(file.path)
                it.copy(
                    file = byteArrayOf(),
                    url = postId + "/" + it.name,
                    type = it.type,
                    name = it.name
                )
            }
            val wasAcknowledged =
                postRepository.createPost(postRequest = request.toResponse().copy(attachments = attachments))
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict, PostError.SERVER_ERROR)
                return@post
            }
            call.respond(HttpStatusCode.OK, message = "Post created successfully")
        }
    }
}