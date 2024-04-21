package com.example.routes.post

import com.example.repository.PostRepository
import io.ktor.server.routing.*

/**
 * This function sets up the routing for the post part of the application.
 * It includes routes for creating a post, adding tags to a post, deleting a post, upvoting a post,
 * downvoting a post, updating a post, getting new posts, and getting all posts.
 *
 * @param postRepository The repository used for accessing the post data.
 */
fun Route.postRouting(
    postRepository: PostRepository
) {
    /**
     * Route for creating a post.
     */
    createPost(
        postRepository = postRepository
    )
    /**
     * Route for adding tags to a post.
     */
    postTags(
        postRepository = postRepository
    )
    /**
     * Route for deleting a post.
     */
    deletePost(
        postRepository = postRepository
    )
    /**
     * Route for upvoting a post.
     */
    upvotePost(
        postRepository = postRepository
    )
    /**
     * Route for downvoting a post.
     */
    downvotePost(
        postRepository = postRepository
    )
    /**
     * Route for updating a post.
     */
    updatePost(
        postRepository = postRepository
    )
    /**
     * Route for getting new posts.
     */
    getNewPosts(
        postRepository = postRepository
    )
    /**
     * Route for getting all posts.
     */
    getAllPosts(
        postRepository = postRepository
    )
}