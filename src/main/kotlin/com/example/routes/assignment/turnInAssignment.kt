package com.example.routes.assignment

import com.example.repository.assignment.AssignmentRepository
import com.example.utils.AssignmentError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.turnInAssignment(
    assignmentRepository: AssignmentRepository
){
    post("turnInAssignment"){
        val request = call.receiveNullable<String>()?: return@post call.respond(HttpStatusCode.BadRequest,AssignmentError.SERVER_ERROR)
        val canSubmit = assignmentRepository.canSubmit(request)
        if(canSubmit.not()){
            call.respond(HttpStatusCode.RequestTimeout,AssignmentError.SERVER_ERROR)
            return@post
        }
        val result = assignmentRepository.turnInAssignment(request)
        if(result){
            call.respond(HttpStatusCode.OK)
        }else{
            call.respond(HttpStatusCode.InternalServerError,AssignmentError.SERVER_ERROR)
        }
    }
}
