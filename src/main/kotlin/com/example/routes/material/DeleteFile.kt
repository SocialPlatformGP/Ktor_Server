package com.example.routes.material

import com.example.data.requests.MaterialRequest
import com.example.repository.material.MaterialRepository
import com.example.utils.EndPoint
import com.example.utils.MaterialError
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
            call.respond(HttpStatusCode.BadRequest,MaterialError.SERVER_ERROR)
            return@post
        }
        val response = materialRepository.deleteFile(request.fileId, request.path)
        call.respond(response)
    }
}