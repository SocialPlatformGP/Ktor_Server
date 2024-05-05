package com.example.routes.assignment

import com.example.data.requests.AssignmentRequest
import com.example.repository.AssignmentRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.submitReview(
    assignmentRepository: AssignmentRepository
){
    post("submitReview"){
        val request = call.receiveNullable<AssignmentRequest.SubmitReview>()?: return@post call.respond(HttpStatusCode.BadRequest)
        val result = assignmentRepository.submitReview(request)
        if(result){
            call.respond(HttpStatusCode.OK)
        }else{
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}