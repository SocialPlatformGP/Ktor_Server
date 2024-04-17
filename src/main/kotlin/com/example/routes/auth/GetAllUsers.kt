package com.example.routes.auth

import com.example.repository.AuthRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllUsers(
    authRepository: AuthRepository
) {
    get(EndPoint.Auth.GetAllUsers.route) {
        val users = authRepository.getAllUsers()
        println("users" + users)
        call.respond(status = HttpStatusCode.OK, users.map { it.toResponse() })
    }
}