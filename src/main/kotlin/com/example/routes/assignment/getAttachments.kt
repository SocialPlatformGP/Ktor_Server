package com.example.routes.assignment

import com.example.data.requests.AssignmentRequest
import com.example.repository.assignment.AssignmentRepository
import com.example.utils.AssignmentError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAttachments(
    assignmentRepository: AssignmentRepository
) {
    post("getAttachments") {
        val request = call.receiveNullable<AssignmentRequest.GetUserAttachmentsRequest>() ?: return@post call.respond(
            HttpStatusCode.BadRequest,AssignmentError.SERVER_ERROR
        )
        val result = assignmentRepository.getAttachments(request.userId, request.assignmentId)
        if (result != null) {
            call.respond(HttpStatusCode.OK, result)
        } else {
            call.respond(HttpStatusCode.InternalServerError, AssignmentError.SERVER_ERROR)
        }


    }
}