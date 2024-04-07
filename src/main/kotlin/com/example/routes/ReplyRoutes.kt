package com.example.routes

import com.example.data.requests.AddTagRequest
import com.example.data.requests.ReplyRequest
import com.example.data.requests.UpdateOrDeletePostRequest
import com.example.data.responses.PostResponse
import com.example.repository.PostRepository
import com.example.repository.ReplyRepository
import com.example.utils.FileUtils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.io.File
import java.util.*

fun Route.createReply(
    replyRepository: ReplyRepository
) {
    post("createReply") {
        val request = call.receiveNullable<ReplyRequest.CreateRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, message = "Can't receive the json")
            return@post
        }
        val fieldsBlank = request.reply.content.isBlank()

        if (fieldsBlank) {
            call.respond(HttpStatusCode.Conflict, message = "Fields required")
            return@post
        }
        val wasAcknowledged = replyRepository.createReply(request)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict, message = "Error Creating the reply")
            return@post
        }
        call.respond(HttpStatusCode.OK, message = "Reply created successfully")

    }
}

fun Route.fetchReplies(
    replyRepository: ReplyRepository
) {
    get("fetchReplies") {
        val request = call.receiveNullable<ReplyRequest.FetchRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, message = "Can't receive the json")
            return@get
        }
        replyRepository.fetchReplies(request).collect{
            call.respond(HttpStatusCode.OK, it)
        }

    }

}

fun Route.updateReply(
    replyRepository: ReplyRepository
) {
    post("updateReply") {
        val request = call.receiveNullable<ReplyRequest.UpdateRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")
            return@post
        }
        val wasAcknowledged = replyRepository.updateReply(request)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict, message = "Error Updating the reply")
            return@post
        }
        call.respond(HttpStatusCode.OK, message = "Reply updated successfully")

    }
}

fun Route.downvoteReply(
    replyRepository: ReplyRepository
) {
    post("downvoteReply") {
        val request = call.receiveNullable<ReplyRequest.DownvoteRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")
            return@post
        }
        val wasAcknowledged = replyRepository.downvoteReply(request)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict, message = "Error Downvoting the reply")
            return@post
        }
        call.respond(HttpStatusCode.OK, message = "Reply downvoted successfully")
    }
}

fun Route.deleteReply(
    replyRepository: ReplyRepository
) {
    delete("deleteReply") {
        val request = call.receiveNullable<ReplyRequest.DeleteRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")
            return@delete
        }
        val wasAcknowledged = replyRepository.deleteReply(request)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict, message = "Error Deleting the reply")
            return@delete
        }
        call.respond(HttpStatusCode.OK, message = "Reply deleted successfully")
    }
}

fun Route.upvoteReply(
    replyRepository: ReplyRepository
) {
    put("upvoteReply") {
        val request = call.receiveNullable<ReplyRequest.UpvoteRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "مش عارف استقبل الjson")
            return@put
        }
        val wasAcknowledged = replyRepository.upvoteReply(request)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict, message = "Error Upvoting the reply")
            return@put
        }
        call.respond(HttpStatusCode.OK, message = "Reply upvoted successfully")

    }

}


