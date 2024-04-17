package com.example.routes.auth

import com.example.repository.AuthRepository
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.token.TokenConfig
import io.ktor.server.routing.*

fun Route.authRouting(
    hashingService: HashingService,
    authRepository: AuthRepository,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    signIn(
        authRepository = authRepository,
        tokenService = tokenService,
        hashingService = hashingService,
        tokenConfig = tokenConfig
    )
    signUp(
        hashingService = hashingService,
        authRepository = authRepository,
        tokenService = tokenService,
        tokenConfig = tokenConfig
    )
    getSignedUser(
        authRepository = authRepository
    )
    checkEmailAvailable(
        authRepository = authRepository
    )
    getAllUsers(
        authRepository = authRepository
    )
    getUsersByIds(
        authRepository = authRepository
    )
}