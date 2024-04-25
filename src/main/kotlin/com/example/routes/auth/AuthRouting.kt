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
 * @param authRepository The repository used for accessing the authentication data.
 */
fun Route.authRouting(
    authRepository: AuthRepository,

) {

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
    createUsers(
        authRepository = authRepository
    )
}