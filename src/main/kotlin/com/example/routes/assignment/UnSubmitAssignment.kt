package com.example.routes.assignment

import com.example.repository.AssignmentRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.unSubmitAssignment(
    assignmentRepository: AssignmentRepository
){
    post("unSubmitAssignment"){
        val request = call.receiveNullable<String>()?: return@post call.respond(HttpStatusCode.BadRequest)
        val result = assignmentRepository.unSubmitAssignment(request)
        if(result){
            call.respond(HttpStatusCode.OK)
        }else{
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}