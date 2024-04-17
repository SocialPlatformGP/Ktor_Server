package com.example.routes.material

import com.example.data.models.material.MaterialFolder
import com.example.data.requests.CreateFolderRequest
import com.example.repository.MaterialRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.uploadFolder(
    materialRepository: MaterialRepository
) {
    post(EndPoint.Media.UploadFolder.route) {
        val request = call.receiveNullable<CreateFolderRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, message = "Can't receive the json")
            return@post
        }
        val folder = MaterialFolder(
            name = request.name,
            path = request.path
        )
        val response = materialRepository.createMaterialFolder(folder)
        call.respond(HttpStatusCode.OK, response)
    }
}