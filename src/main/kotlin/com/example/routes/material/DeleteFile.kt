package com.example.routes.material

import com.example.data.requests.MaterialRequest
import com.example.repository.MaterialRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteFile(
    materialRepository: MaterialRepository
) {
    post(EndPoint.Media.DeleteFile.route) {
        val request = call.receiveNullable<MaterialRequest.DeleteFileRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, message = "Can't receive the json")
            return@post
        }
        val response = materialRepository.deleteFile(request.fileId, request.path)
        call.respond(response)
    }
}