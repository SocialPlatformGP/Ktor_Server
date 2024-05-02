package com.example.routes.assignment

import com.example.data.requests.AssignmentRequest
import com.example.data.requests.PostRequest
import com.example.repository.AssignmentRepository
import io.ktor.server.routing.*
import com.example.utils.EndPoint
import com.example.utils.FileUtils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.io.File
import java.util.*

fun Route.createAssignment(
    assignmentRepo: AssignmentRepository
) {
    post (EndPoint.Assignment.CreateAssignment.route){
        val request = call.receiveNullable<AssignmentRequest.CreateRequest>()?.assignment ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, message = "Can't receive the json")
            return@post
        }
        val fieldsBlank = request.title.isBlank() || request.creatorId.isBlank() || request.communityId.isBlank()
        if (fieldsBlank) {
            println("fieldsBlank")
            call.respond(HttpStatusCode.Conflict, message = "Fields required")
            return@post
        }
        if (request.attachments.isEmpty()) {
            val assignmentId = assignmentRepo.createAssignment(request)
            println("assignmentId: $assignmentId")
            call.respond(HttpStatusCode.OK, assignmentId)
            return@post
        } else {
            val assignmentId = UUID.randomUUID().toString()
            val folder = File("files/assignments/${assignmentId}")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val attachments = request.attachments.map {
                val file = FileUtils.saveByteArrayToFile(it.byteArray, "files/assignments/${assignmentId}" + it.name)
                println(file.path)
                it.copy(
                    byteArray = byteArrayOf(),
                    url = assignmentId + "/" + it.name,
                    type = it.type,
                    name = it.name
                )
            }
            val assId =
                assignmentRepo.createAssignment(request = request.copy(attachments = attachments))
            call.respond(HttpStatusCode.OK, assId)
        }
    }
}