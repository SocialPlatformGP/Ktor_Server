package com.example.routes.auth

import com.example.data.requests.LoginRequest
import com.example.data.responses.AuthResponse
import com.example.repository.AuthRepository
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.hashing.SaltedHash
import com.example.security.token.TokenClaim
import com.example.security.token.TokenConfig
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signIn(
    authRepository: AuthRepository, hashingService: HashingService, tokenService: TokenService, tokenConfig: TokenConfig
) {
    post(EndPoint.Auth.SignIn.route) {
        val request = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respond(
                HttpStatusCode.BadRequest, AuthResponse(
                    errorMessage = "Cannot parse request", token = "", userId = ""
                )
            )
            return@post
        }
        val fieldsBlank = request.email.isBlank() || request.password.isBlank()

        if (fieldsBlank) {
            call.respond(
                HttpStatusCode.Conflict, AuthResponse(
                    errorMessage = "Fields required", token = "", userId = ""
                )
            )
            return@post
        }

        val user = authRepository.findUserByEmail(request.email)
        if (user == null) {
            call.respond(
                HttpStatusCode.Conflict, AuthResponse(
                    errorMessage = "User does not exist with this email", token = "", userId = ""
                )
            )
            return@post
        }

        val isValidPassword = hashingService.verify(
            plainText = request.password, saltedHash = SaltedHash(
                hash = user.password, salt = user.salt
            )
        )

        if (!isValidPassword) {
            call.respond(
                HttpStatusCode.Conflict, AuthResponse(
                    errorMessage = "Invalid password", token = "", userId = ""
                )
            )
            return@post
        }
        val token = tokenService.generateToken(
            config = tokenConfig,
            TokenClaim(name = "userId", value = user.id),
            TokenClaim(name = "email", value = user.email)
        )

        call.respond(
            status = HttpStatusCode.OK, AuthResponse(
                errorMessage = "", token = token, userId = user.id
            )
        )

    }


}