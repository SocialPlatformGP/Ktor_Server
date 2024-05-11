package com.example.routes.material

import com.example.data.requests.MaterialRequest
import com.example.data.responses.MaterialResponse
import com.example.utils.MaterialError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File


fun Route.downloadFile() {
    post("/download") {
        val request = call.receive<MaterialRequest.DownloadFileRequest>()
        val file = File("files/"+request.url)
        if(file.exists()) {
            call.respond(HttpStatusCode.OK,MaterialResponse.DownloadFileResponse(file.readBytes(), file.name))
        } else {
            call.respond(HttpStatusCode.NotFound,MaterialError.SERVER_ERROR)
        }
    }
}
