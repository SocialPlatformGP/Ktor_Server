package com.example.routes.assignment

import com.example.data.models.assignment.Assignment
import com.example.data.requests.AssignmentRequest
import com.example.repository.AssignmentRepository
import com.example.repository.CommunityRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAssignment(
    assignmentRepository: AssignmentRepository,
) {
    post("getAssignmentById") {
        val request = call.receiveNullable<AssignmentRequest.GetAssignmentById>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        val result = assignmentRepository.getAssignment(request.assignmentId)
        if (result != null) {
            call.respond(HttpStatusCode.OK, result)
        } else {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}