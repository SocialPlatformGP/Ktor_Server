package com.example.routes.auth

import com.example.data.requests.GetUserRequest
import com.example.data.requests.GetUsersByIdsRequest
import com.example.repository.AuthRepository
import com.example.utils.DataError
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUsersByIds(
    authRepository: AuthRepository,
    ) {
    post(EndPoint.Auth.GetUsersByIds.route){
        val request = call.receiveNullable<GetUsersByIdsRequest>() ?: return@post call.respond(HttpStatusCode.BadRequest,DataError.Network.BAD_REQUEST)
        val result = authRepository.getUsersByIds(request.ids)
        call.respond(HttpStatusCode.OK,result)


    }
}