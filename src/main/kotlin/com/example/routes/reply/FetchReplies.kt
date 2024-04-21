package com.example.routes.reply

import com.example.data.requests.ReplyRequest
import com.example.repository.ReplyRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.fetchReplies(
    replyRepository: ReplyRepository
) {
    post(EndPoint.Reply.FetchReplies.route) {
        val request = call.receiveNullable<ReplyRequest.FetchRequest>() ?:return@post
            call.respond(HttpStatusCode.BadRequest, message = "Can't receive the json")
        println("\n\n\n\n\n\n\n\n\n\nrequesT: $request\n\n\n\n\n\n\n\n\n\n\n\n")
        val result = replyRepository.fetchReplies(request)
        println("result: $result")
        call.respond(HttpStatusCode.OK, result)
    }

}