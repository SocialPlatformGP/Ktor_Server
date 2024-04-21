package com.example.routes.auth

import com.example.data.requests.CheckExistUserRequest
import com.example.data.responses.IsEmailAvailableResponse
import com.example.repository.AuthRepository
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * This function sets up the route for checking if an email is available.
 *
 * @param authRepository The repository used for accessing the authentication data.
 */
fun Route.checkEmailAvailable(
    authRepository: AuthRepository,
) {
    /**
     * POST route for checking if an email is available.
     * It receives a CheckExistUserRequest and responds with an IsEmailAvailableResponse.
     * If the request cannot be parsed, it responds with a bad request status and a message.
     * If the email field is blank, it responds with a conflict status and a message.
     * If a user with the requested email exists, it responds with an OK status and a message indicating that the email is not available.
     * If a user with the requested email does not exist, it responds with an OK status and a message indicating that the email is available.
     */
    post(EndPoint.Auth.CheckAvailability.route) {
        val request = call.receiveNullable<CheckExistUserRequest>() ?: kotlin.run {
            call.respond(
                HttpStatusCode.BadRequest, IsEmailAvailableResponse(
                    isAvailable = false,
                    message = "Cannot parse request",
                )
            )
            return@post
        }
        val fieldsBlank = request.email.isBlank()
        if (fieldsBlank) {
            call.respond(
                HttpStatusCode.Conflict, IsEmailAvailableResponse(
                    isAvailable = false,
                    message = "Fields required",
                )
            )
            return@post
        }
        val user = authRepository.findUserByEmail(request.email)
        if (user != null) {
            call.respond(
                HttpStatusCode.OK, IsEmailAvailableResponse(
                    isAvailable = false,
                    message = "User already exists with this email",
                )
            )
            return@post
        } else {
            call.respond(
                call.respond(
                    HttpStatusCode.OK, IsEmailAvailableResponse(
                        isAvailable = true,
                        message = "User does not exist with this email and can be used to sign up",
                    )
                )

            )
        }
    }
}