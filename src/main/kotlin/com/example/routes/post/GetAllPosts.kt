package com.example.routes.post

import com.example.repository.PostRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllPosts(
    postRepository: PostRepository
) {
    get(EndPoint.Post.GetAllPosts.route) {
        val posts = postRepository.getPosts()
        call.respond(HttpStatusCode.OK, posts)
    }
}