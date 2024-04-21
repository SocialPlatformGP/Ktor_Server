package com.example.routes.reply

import com.example.repository.ReplyRepository
import io.ktor.server.routing.*

/**
 * This function sets up the routing for the reply part of the application.
 * It includes routes for creating a reply, fetching replies, updating a reply, deleting a reply,
 * upvoting a reply, and downvoting a reply.
 *
 * @param replyRepository The repository used for accessing the reply data.
 */
fun Route.replyRouting(
    replyRepository: ReplyRepository
) {
    /**
     * Route for creating a reply.
     */
    createReply(
        replyRepository = replyRepository
    )
    /**
     * Route for fetching replies.
     */
    fetchReplies(
        replyRepository = replyRepository
    )
    /**
     * Route for updating a reply.
     */
    updateReply(
        replyRepository = replyRepository
    )
    /**
     * Route for deleting a reply.
     */
    deleteReply(
        replyRepository = replyRepository
    )
    /**
     * Route for upvoting a reply.
     */
    upvoteReply(
        replyRepository = replyRepository
    )
    /**
     * Route for downvoting a reply.
     */
    downvoteReply(
        replyRepository = replyRepository
    )
}