package com.example.routes.grades

import com.example.data.requests.GradesRequest
import com.example.repository.grade.GradesRepository
import com.example.utils.GradesError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getGrades(gradesRepository: GradesRepository) {
    post("/getUserGrades") {
        val request = call.receiveNullable<GradesRequest.GetGrades>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, GradesError.SERVER_ERROR)
            return@post
        }
        val response = gradesRepository.getGrades(request)
        call.respond(response)
    }
}