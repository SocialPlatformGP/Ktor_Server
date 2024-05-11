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

fun Route.updatePost(
    postRepository: PostRepository
) {
    post(EndPoint.Post.UpdatePost.route) {
        val request = call.receiveNullable<PostRequest.UpdateRequest>() ?: return@post call.respond(
            HttpStatusCode.BadRequest,
            PostError.SERVER_ERROR
        )

        val fieldsBlank = request.post.title.isBlank() || request.post.body.isBlank()

        if (fieldsBlank) {
            println("fieldsBlank")
            call.respond(HttpStatusCode.BadRequest, PostError.SERVER_ERROR)
            return@post
        }

        if (request.post.attachments.isEmpty()) {
            val wasAcknowledged = postRepository.updatePost(postRequest = request)
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict, PostError.SERVER_ERROR)
                return@post
            }
            call.respond(HttpStatusCode.OK)
            return@post
        } else {
            val postId = UUID.randomUUID().toString()
            val folder = File("files/${postId}")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val attachments = request.post.attachments.map {
                if (it.file.isEmpty()) {
                     it
                } else {
                    val file = FileUtils.saveByteArrayToFile(it.file, "files/${postId}/" + it.name)
                    println(file.path)
                    it.copy(
                        file = byteArrayOf(),
                        url = postId + "/" + it.name,
                        type = it.type,
                        name = it.name
                    )
                }
            }
            val updatedPost = request.post.copy(attachments = attachments)
            val wasAcknowledged =
                postRepository.updatePost(postRequest = request.copy(post = updatedPost))
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict, PostError.SERVER_ERROR)
                return@post
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}