package com.example.routes.grades

import com.example.data.requests.GradesRequest
import com.example.repository.grade.GradesRepository
import com.example.utils.CsvUtils
import com.example.utils.FileUtils
import com.example.utils.GradesError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.util.*

fun Route.uploadGradesFile(gradesRepository: GradesRepository) {
    post("/uploadGradesFile") {
        val request = call.receiveNullable<GradesRequest.UploadGradesFile>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, GradesError.SERVER_ERROR)
            return@post
        }
        val fileId = UUID.randomUUID().toString()
        val folder = File("files/grades/${fileId}")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val file = FileUtils.saveByteArrayToFile(request.content, "files/grades/${fileId}/" + request.name)
        println(file.path)
        val grades = CsvUtils.readExcelFileToUsers(file.path).map {
            it.copy(
                course = request.subject,
            )
        }
        val response = gradesRepository.uploadGradesFile(grades)
        if (response) {
            call.respond(HttpStatusCode.OK, message = "Grades uploaded successfully")
        } else {
            call.respond(HttpStatusCode.InternalServerError, GradesError.SERVER_ERROR)
        }
    }
}