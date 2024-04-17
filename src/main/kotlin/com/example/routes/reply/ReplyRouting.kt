package com.example.routes.reply

import com.example.repository.ReplyRepository
import com.example.routes.reply.*
import io.ktor.server.routing.*

fun Route.replyRouting(
    replyRepository: ReplyRepository
) {
    createReply(
        replyRepository = replyRepository
    )
    fetchReplies(
        replyRepository = replyRepository
    )
    updateReply(
        replyRepository = replyRepository
    )
    deleteReply(
        replyRepository = replyRepository
    )
    upvoteReply(
        replyRepository = replyRepository
    )
    downvoteReply(
        replyRepository = replyRepository
    )
}