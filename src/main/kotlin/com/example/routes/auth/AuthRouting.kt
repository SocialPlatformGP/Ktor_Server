package com.example.routes.auth

import com.example.repository.AuthRepository
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.token.TokenConfig
import io.ktor.server.routing.*

/**
 * This function sets up the routing for the authentication part of the application.
 * It includes routes for signing in, signing up, getting the signed in user, checking if an email is available,
 * getting all users, and getting users by their IDs.
 *
 * @param hashingService The service used for hashing passwords.
 * @param authRepository The repository used for accessing the authentication data.
 * @param tokenService The service used for handling tokens.
 * @param tokenConfig The configuration for the tokens.
 */
fun Route.authRouting(
    hashingService: HashingService,
    authRepository: AuthRepository,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    /**
     * Route for signing in a user.
     */
    signIn(
        authRepository = authRepository,
        tokenService = tokenService,
        hashingService = hashingService,
        tokenConfig = tokenConfig
    )
    /**
     * Route for signing up a new user.
     */
    signUp(
        hashingService = hashingService,
        authRepository = authRepository,
        tokenService = tokenService,
        tokenConfig = tokenConfig
    )
    /**
     * Route for getting the currently signed in user.
     */
    getSignedUser(
        authRepository = authRepository
    )
    /**
     * Route for checking if an email is available.
     */
    checkEmailAvailable(
        authRepository = authRepository
    )
    /**
     * Route for getting all users.
     */
    getAllUsers(
        authRepository = authRepository
    )
    /**
     * Route for getting users by their IDs.
     */
    getUsersByIds(
        authRepository = authRepository
    )
}