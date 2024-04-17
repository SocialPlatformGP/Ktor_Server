package com.example.routes.material

import com.example.data.models.material.MaterialFile
import com.example.data.requests.CreateFileRequest
import com.example.repository.MaterialRepository
import com.example.utils.EndPoint
import com.example.utils.FileUtils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.uploadFile(
    materialRepository: MaterialRepository
) {
    post(EndPoint.Media.UploadFile.route) {
        val request = call.receiveNullable<CreateFileRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, message = "Can't receive the json")
            return@post
        }
        val folder = File("files/${request.path}")
        if (!folder.exists()) {
            folder.mkdirs()
        }

        val file = FileUtils.saveByteArrayToFile(request.content, "files/${request.path}/" + request.name)
        println(file.path)

        val response = materialRepository.createMaterialFile(
            MaterialFile(
                path = request.path,
                url = request.path + "/" + request.name,
                type = file.extension,
                name = request.name
            )
        )
        call.respond(response)

    }
}