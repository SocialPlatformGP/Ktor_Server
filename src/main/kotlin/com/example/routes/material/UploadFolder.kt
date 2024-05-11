package com.example.routes.material

import com.example.data.models.material.MaterialFolder
import com.example.data.requests.MaterialRequest
import com.example.repository.material.MaterialRepository
import com.example.utils.EndPoint
import com.example.utils.MaterialError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.uploadFolder(
    materialRepository: MaterialRepository
) {
    post(EndPoint.Media.UploadFolder.route) {
        val request = call.receiveNullable<MaterialRequest.CreateFolderRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest,MaterialError.SERVER_ERROR)
            return@post
        }
        println(request)
        val folder = MaterialFolder(
            name = request.name,
            path = request.path,
            communityId = request.communityId
        )
        val response = materialRepository.createMaterialFolder(folder)
        call.respond(HttpStatusCode.OK, response)
    }
}