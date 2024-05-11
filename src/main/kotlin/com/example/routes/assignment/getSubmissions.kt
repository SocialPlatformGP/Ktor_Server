package com.example.routes.assignment

import com.example.data.requests.AssignmentRequest
import com.example.repository.assignment.AssignmentRepository
import com.example.utils.AssignmentError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getSubmissions(
    assignmentRepository: AssignmentRepository
) {
    post("getSubmissions") {
        val request = call.receiveNullable<AssignmentRequest.GetAssignmentSubmissions>() ?: return@post call.respond(
            HttpStatusCode.BadRequest,AssignmentError.SERVER_ERROR
        )
        val result = assignmentRepository.getSubmissions(request.assignmentId)
        call.respond(HttpStatusCode.OK, result)

    }
}