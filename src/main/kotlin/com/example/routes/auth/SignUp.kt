package com.example.routes.auth

import com.example.data.models.user.User
import com.example.data.requests.SignUpRequest
import com.example.data.responses.AuthResponse
import com.example.repository.AuthRepository
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.token.TokenClaim
import com.example.security.token.TokenConfig
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signUp(
    hashingService: HashingService,
    authRepository: AuthRepository,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    post(EndPoint.Auth.SignUp.route) {
        val request = call.receiveNullable<SignUpRequest>() ?: return@post
        call.respond(HttpStatusCode.BadRequest, AuthResponse(errorMessage = "Cannot parse request", userId = "", token = ""))
        val fieldsBlank = request.phoneNumber.isBlank() || request.firstName.isBlank() || request.lastName.isBlank() || request.bio.isBlank() || request.birthdate == 0L

        if (fieldsBlank) {
            return@post  call.respond(HttpStatusCode.Conflict, AuthResponse(errorMessage = "Fields required", token = "", userId = ""))
        }

        val saltedHash = hashingService.generateHash(request.password)
        val user = User(
            firstName = request.firstName,
            password = saltedHash.hash,
            email = request.email,
            salt = saltedHash.salt,
            birthdate = request.birthdate,
            bio = request.bio,
            profilePictureURL = request.profilePictureURL,
            phoneNumber = request.phoneNumber
        )

        val newUser = authRepository.createUser(user = user)
        if (newUser == null) {
            return@post call.respond(HttpStatusCode.Conflict, AuthResponse(errorMessage = "Server error, please try again later", token = "", userId = ""))

        } else {
            val token = tokenService.generateToken(
                config = tokenConfig,
                TokenClaim(name = "userId", value = newUser.id),
                TokenClaim(name = "email", value = newUser.email)
            )
            call.respond(HttpStatusCode.OK, AuthResponse(errorMessage = "", token = token, userId = newUser.id))
        }
    }
}