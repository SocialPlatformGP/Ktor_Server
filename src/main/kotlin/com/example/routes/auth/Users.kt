package com.example.routes.auth

import com.example.data.requests.GetUserRequest
import com.example.data.responses.UserResponse
import com.example.repository.AuthRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getSignedUser(
    authRepository: AuthRepository,
) {
    get(EndPoint.Auth.GetSignedUser.route) {
        val request = call.receiveNullable<GetUserRequest>() ?: return@get call.respond(HttpStatusCode.BadRequest)
        val fieldsBlank = request.id.isBlank()

        if (fieldsBlank) {
           return@get call.respond(HttpStatusCode.Conflict, UserResponse())
        }

        val user = authRepository.findUserById(request.id) ?: return@get call.respond(HttpStatusCode.Conflict, UserResponse())
        call.respond(status = HttpStatusCode.OK, user.toResponse())
    }
}

