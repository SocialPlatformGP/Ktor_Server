package com.example.routes.auth

import com.example.data.models.user.User
import com.example.data.requests.UpdateUserRequest
import com.example.repository.user.AuthRepository
import com.example.utils.AuthError
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
        val request = call.receiveNullable<User>() ?: return@post call.respond(HttpStatusCode.BadRequest,AuthError.SERVER_ERROR)
        println("\n\n\n $request \n\n\n")
        val insertIsSuccessful = authRepository.createUser(request)
        if(!insertIsSuccessful){
            call.respond(HttpStatusCode.NotFound,AuthError.SERVER_ERROR)
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }
    post("updatePhoneNumber") {
        val request = call.receiveNullable<UpdateUserRequest.UpdatePhoneNumber>() ?: return@post call.respond(HttpStatusCode.BadRequest, AuthError.SERVER_ERROR)
        val updateIsSuccessful = authRepository.updatePhoneNumber(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, AuthError.SERVER_ERROR)
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }
    post("updateName") {
        val request = call.receiveNullable<UpdateUserRequest.UpdateName>() ?: return@post call.respond(HttpStatusCode.BadRequest, AuthError.SERVER_ERROR)
        val updateIsSuccessful = authRepository.updateName(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, AuthError.SERVER_ERROR)
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }
    post ("updateTheme"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateTheme>() ?: return@post call.respond(HttpStatusCode.BadRequest, AuthError.SERVER_ERROR)
        val updateIsSuccessful = authRepository.updateTheme(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound,AuthError.SERVER_ERROR)
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }
    post ("updateAllowMessagesFrom"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateAllowMessagesFrom>() ?: return@post call.respond(HttpStatusCode.BadRequest,AuthError.SERVER_ERROR)
        val updateIsSuccessful = authRepository.updateAllowMessagesFrom(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound,AuthError.SERVER_ERROR)
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }
    post ("updateWhoCanAddToGroups"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateWhoCanAddToGroups>() ?: return@post call.respond(HttpStatusCode.BadRequest, AuthError.SERVER_ERROR)
        val updateIsSuccessful = authRepository.updateWhoCanAddToGroups(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound,AuthError.SERVER_ERROR)
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }
    post ("updateIsNotificationsAllowed"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateIsNotificationsAllowed>() ?: return@post call.respond(HttpStatusCode.BadRequest, AuthError.SERVER_ERROR)
        val updateIsSuccessful = authRepository.updateIsNotificationsAllowed(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound,AuthError.SERVER_ERROR)
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }
    post ("updateIsPostNotificationsAllowed"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateIsPostNotificationsAllowed>() ?: return@post call.respond(HttpStatusCode.BadRequest, AuthError.SERVER_ERROR)
        val updateIsSuccessful = authRepository.updateIsPostNotificationsAllowed(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, AuthError.SERVER_ERROR)
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }
    post ("updateIsChatNotificationsAllowed"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateIsChatNotificationsAllowed>() ?: return@post call.respond(HttpStatusCode.BadRequest, AuthError.SERVER_ERROR)
        val updateIsSuccessful = authRepository.updateIsChatNotificationsAllowed(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound,AuthError.SERVER_ERROR)
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }
    post ("updateIsAssignmentsNotificationsAllowed"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateIsAssignmentsNotificationsAllowed>() ?: return@post call.respond(HttpStatusCode.BadRequest, AuthError.SERVER_ERROR)
        val updateIsSuccessful = authRepository.updateIsAssignmentsNotificationsAllowed(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, AuthError.SERVER_ERROR)
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }
    post ("updateIsCalendarNotificationsAllowed"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateIsCalendarNotificationsAllowed>() ?: return@post call.respond(HttpStatusCode.BadRequest, AuthError.SERVER_ERROR)
        val updateIsSuccessful = authRepository.updateIsCalendarNotificationsAllowed(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, AuthError.SERVER_ERROR)
        } else {
            call.respond(HttpStatusCode.OK  )
        }
    }
    post("updateEmail"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateEmail>() ?: return@post call.respond(HttpStatusCode.BadRequest, AuthError.SERVER_ERROR)
        val updateIsSuccessful = authRepository.updateEmail(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, AuthError.SERVER_ERROR)
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }
    post("updateAvatarUrl"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateAvatarUrl>() ?: return@post call.respond(HttpStatusCode.BadRequest, AuthError.SERVER_ERROR)
        val updateIsSuccessful = authRepository.updateAvatarUrl(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, AuthError.SERVER_ERROR)
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }
    post("updateBio"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateBio>() ?: return@post call.respond(HttpStatusCode.BadRequest, AuthError.SERVER_ERROR)
        val updateIsSuccessful = authRepository.updateBio(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, AuthError.SERVER_ERROR)
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }


}