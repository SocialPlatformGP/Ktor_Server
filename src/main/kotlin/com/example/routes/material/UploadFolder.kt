package com.example.routes.material

import com.example.data.models.material.MaterialFolder
import com.example.data.requests.MaterialRequest
import com.example.repository.MaterialRepository
import com.example.utils.DataError
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
        val request = call.receiveNullable<MaterialRequest.CreateFolderRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest,DataError.Network.BAD_REQUEST)
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