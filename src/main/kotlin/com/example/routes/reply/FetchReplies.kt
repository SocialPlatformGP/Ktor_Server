package com.example.routes.reply

import com.example.data.requests.ReplyRequest
import com.example.repository.reply.ReplyRepository
import com.example.utils.EndPoint
import com.example.utils.ReplyError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.fetchReplies(
    replyRepository: ReplyRepository
) {
    post(EndPoint.Reply.FetchReplies.route) {
        val request = call.receiveNullable<ReplyRequest.FetchRequest>() ?: return@post call.respond(HttpStatusCode.BadRequest,ReplyError.SERVER_ERROR)
        println("\n\n\n\n\n\n\n\n\n\nrequesT: $request\n\n\n\n\n\n\n\n\n\n\n\n")
        val result = replyRepository.fetchReplies(request)
        println("result: $result")
        call.respond(HttpStatusCode.OK, result)
    }

}