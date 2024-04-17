package com.example.routes.post

import com.example.repository.PostRepository
import io.ktor.server.routing.*

fun Route.postRouting(
    postRepository: PostRepository
) {
    createPost(
        postRepository = postRepository
    )
    postTags(
        postRepository = postRepository
    )
    deletePost(
        postRepository = postRepository
    )
    upvotePost(
        postRepository = postRepository
    )
    downvotePost(
        postRepository = postRepository
    )
    updatePost(
        postRepository = postRepository
    )
    getNewPosts(
        postRepository = postRepository
    )
    getAllPosts(
        postRepository = postRepository
    )
}