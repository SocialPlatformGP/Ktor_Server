package com.example.routes.assignment

import com.example.data.requests.AssignmentRequest
import com.example.repository.AssignmentRepository
import com.example.utils.FileUtils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.util.*

fun Route.submitAssignment(
    assignmentRepository: AssignmentRepository
) {
    post("submitAssignment") {
        val request = call.receiveNullable<AssignmentRequest.SubmitRequest>()
            ?: return@post call.respond(HttpStatusCode.BadRequest)
        if (request.attachments.isEmpty()) {
            val result = assignmentRepository.submitAssignment(request.assignmentId, request.userId, request.attachments)
            if (result) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.InternalServerError)
            }
        } else {
            val assignmentId = UUID.randomUUID().toString()
            val folder = File("files/assignments/${assignmentId}")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val attachments = request.attachments.map {
                FileUtils.saveByteArrayToFile(it.byteArray, "files/assignments/${assignmentId}/" + it.name)
                it.copy(
                    byteArray = byteArrayOf(),
                    url = "assignments/" + assignmentId + "/" + it.name,
                    type = it.type,
                    name = it.name
                )
            }
            val result = assignmentRepository.submitAssignment(request.assignmentId, request.userId,attachments)
            if (result) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

    }
}