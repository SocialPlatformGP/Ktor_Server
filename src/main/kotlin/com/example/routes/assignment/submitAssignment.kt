package com.example.routes.assignment

import com.example.data.requests.AssignmentRequest
import com.example.repository.AssignmentRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.submitAssignment(
    assignmentRepository: AssignmentRepository
){
    post("submitAssignment") {
        val request = call.receiveNullable<AssignmentRequest.SubmitRequest>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        val result = assignmentRepository.submitAssignment(request.userId, request.assignmentId, request.attachments)
        if (result) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}