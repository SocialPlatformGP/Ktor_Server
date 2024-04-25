package com.example.routes.auth

import com.example.data.models.user.User
import com.example.repository.AuthRepository
import com.example.utils.DataError
import com.example.utils.DataSuccess
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createUsers(
    authRepository: AuthRepository
) {
    post(EndPoint.Auth.CreateUser.route){
        val request = call.receiveNullable<User>() ?: return@post call.respond(HttpStatusCode.BadRequest,DataError.Network.BAD_REQUEST)
        println("\n\n\n $request \n\n\n")
        val insertIsSuccessful = authRepository.createUser(request)
        if(!insertIsSuccessful){
            call.respond(HttpStatusCode.NotFound,DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK,DataSuccess.User.CREATED_SUCCESSFULLY)
        }
    }

}