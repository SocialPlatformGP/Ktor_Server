package com.example.routes.grades

import com.example.data.requests.GradesRequest
import com.example.repository.GradesRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getGrades(gradesRepository: GradesRepository) {
    post("/getUserGrades") {
        val request = call.receiveNullable<GradesRequest.GetGrades>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, message = "Can't receive the json")
            return@post
        }
        val response = gradesRepository.getGrades(request)
        call.respond(response)
    }
}