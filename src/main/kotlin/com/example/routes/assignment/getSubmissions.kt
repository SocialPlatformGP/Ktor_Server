package com.example.routes.assignment

import com.example.data.requests.AssignmentRequest
import com.example.repository.AssignmentRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getSubmissions(
    assignmentRepository: AssignmentRepository
) {
    post("getSubmissions") {
        val request = call.receiveNullable<String>() ?: return@post call.respond(
            HttpStatusCode.BadRequest
        )
        val result = assignmentRepository.getSubmissions(request)
        call.respond(HttpStatusCode.OK, result)

    }
}