package com.example.routes.material

import com.example.data.models.material.MaterialFile
import com.example.data.requests.MaterialRequest
import com.example.repository.material.MaterialRepository
import com.example.utils.EndPoint
import com.example.utils.FileUtils
import com.example.utils.MaterialError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt

fun Route.uploadFile(
    materialRepository: MaterialRepository
) {
    post(EndPoint.Media.UploadFile.route) {
        val request = call.receiveNullable<MaterialRequest.CreateFileRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, MaterialError.SERVER_ERROR)
            return@post
        }
        val folder = File("files/${request.path}")
        if (!folder.exists()) {
            folder.mkdirs()
        }

        val file = FileUtils.saveByteArrayToFile(request.content, "files/${request.path}/" + request.name)
        println(file.path)
        println("\n\n\n Name: ${request.name} , size = ${request.content.size}\n\n\n")
        val response = materialRepository.createMaterialFile(
            MaterialFile(
                path = request.path,
                url = request.path + "/" + request.name,
                size = sizeToString(request.content.size),
                type = file.extension,
                name = request.name,
                communityId = request.communityId
            )
        )
        call.respond(response)

    }
}
fun sizeToString(sizeInBytes: Int): String{
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    if (sizeInBytes <= 0) return "0 B"
    if(sizeInBytes < 1024) return "1 KB"
    val digitGroups = (log10(sizeInBytes.toDouble()) / log10(1024.0)).toInt()
    return "%.1f %s".format((sizeInBytes / 1024.0.pow(digitGroups.toDouble())).roundToInt(), units[digitGroups])
}