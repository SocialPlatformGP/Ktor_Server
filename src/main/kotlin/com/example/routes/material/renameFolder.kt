package com.example.routes.material

import com.example.data.requests.MaterialRequest
import com.example.repository.material.MaterialRepository
import com.example.utils.MaterialError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.renameFolder(
    materialRepository: MaterialRepository
) {
    post("renameFolder") {
        val request = call.receiveNullable<MaterialRequest.RenameFolderRequest>() ?: return@post call.respond(
            HttpStatusCode.BadRequest,
            MaterialError.SERVER_ERROR
        )
        val result = materialRepository.renameFolder(request)
        call.respond(HttpStatusCode.OK, result)
    }
}