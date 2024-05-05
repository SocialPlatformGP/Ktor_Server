package com.example.routes.assignment

import com.example.data.models.assignment.Assignment
import com.example.data.requests.AssignmentRequest
import com.example.repository.AssignmentRepository
import com.example.repository.CommunityRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAssignments(
    assignmentRepository: AssignmentRepository,
    communityRepository: CommunityRepository
){
    post("getAssignments"){
        val request = call.receiveNullable<String>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        val userCommunities = communityRepository.getUserCommunities(request)
        val result  = mutableListOf<Assignment>()
        userCommunities.map {
            assignmentRepository.getAssignments(it.id).let {assignments->
                result.addAll(assignments)
            }
        }
        call.respond(HttpStatusCode.OK, result)
    }
}