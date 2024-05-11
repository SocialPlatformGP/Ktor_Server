package com.example.routes.assignment

import com.example.data.requests.AssignmentRequest
import com.example.repository.assignment.AssignmentRepository
import com.example.utils.AssignmentError
import com.example.utils.EndPoint
import com.example.utils.FileUtils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.util.*

fun Route.createAssignment(
    assignmentRepo: AssignmentRepository
) {
    post(EndPoint.Assignment.CreateAssignment.route) {
        val request = call.receiveNullable<AssignmentRequest.CreateRequest>()?.assignment ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, AssignmentError.SERVER_ERROR)
            return@post
        }
        if (request.attachments.isEmpty()) {
            val result = assignmentRepo.createAssignment(request)
            if (result) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.InternalServerError, AssignmentError.SERVER_ERROR)
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
            val result = assignmentRepo.createAssignment(request.copy(attachments = attachments))
            if (result) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.InternalServerError, AssignmentError.SERVER_ERROR)
            }
        }

    }
}