package com.example.routes.auth

import com.example.data.models.user.User
import com.example.data.requests.UpdateUserRequest
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
    post("updatePhoneNumber") {
        val request = call.receiveNullable<UpdateUserRequest.UpdatePhoneNumber>() ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val updateIsSuccessful = authRepository.updatePhoneNumber(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK, DataSuccess.User.UPDATED_SUCCESSFULLY)
        }
    }
    post("updateName") {
        val request = call.receiveNullable<UpdateUserRequest.UpdateName>() ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val updateIsSuccessful = authRepository.updateName(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK, DataSuccess.User.UPDATED_SUCCESSFULLY)
        }
    }
    post ("updateTheme"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateTheme>() ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val updateIsSuccessful = authRepository.updateTheme(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK, DataSuccess.User.UPDATED_SUCCESSFULLY)
        }
    }
    post ("updateAllowMessagesFrom"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateAllowMessagesFrom>() ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val updateIsSuccessful = authRepository.updateAllowMessagesFrom(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK, DataSuccess.User.UPDATED_SUCCESSFULLY)
        }
    }
    post ("updateWhoCanAddToGroups"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateWhoCanAddToGroups>() ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val updateIsSuccessful = authRepository.updateWhoCanAddToGroups(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK, DataSuccess.User.UPDATED_SUCCESSFULLY)
        }
    }
    post ("updateIsNotificationsAllowed"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateIsNotificationsAllowed>() ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val updateIsSuccessful = authRepository.updateIsNotificationsAllowed(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK, DataSuccess.User.UPDATED_SUCCESSFULLY)
        }
    }
    post ("updateIsPostNotificationsAllowed"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateIsPostNotificationsAllowed>() ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val updateIsSuccessful = authRepository.updateIsPostNotificationsAllowed(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK, DataSuccess.User.UPDATED_SUCCESSFULLY)
        }
    }
    post ("updateIsChatNotificationsAllowed"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateIsChatNotificationsAllowed>() ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val updateIsSuccessful = authRepository.updateIsChatNotificationsAllowed(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK, DataSuccess.User.UPDATED_SUCCESSFULLY)
        }
    }
    post ("updateIsAssignmentsNotificationsAllowed"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateIsAssignmentsNotificationsAllowed>() ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val updateIsSuccessful = authRepository.updateIsAssignmentsNotificationsAllowed(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK, DataSuccess.User.UPDATED_SUCCESSFULLY)
        }
    }
    post ("updateIsCalendarNotificationsAllowed"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateIsCalendarNotificationsAllowed>() ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val updateIsSuccessful = authRepository.updateIsCalendarNotificationsAllowed(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK, DataSuccess.User.UPDATED_SUCCESSFULLY)
        }
    }
    post("updateEmail"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateEmail>() ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val updateIsSuccessful = authRepository.updateEmail(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK, DataSuccess.User.UPDATED_SUCCESSFULLY)
        }
    }
    post("updateAvatarUrl"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateAvatarUrl>() ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val updateIsSuccessful = authRepository.updateAvatarUrl(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK, DataSuccess.User.UPDATED_SUCCESSFULLY)
        }
    }
    post("updateBio"){
        val request = call.receiveNullable<UpdateUserRequest.UpdateBio>() ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val updateIsSuccessful = authRepository.updateBio(request)
        if (!updateIsSuccessful) {
            call.respond(HttpStatusCode.NotFound, DataError.Network.NOT_FOUND)
        } else {
            call.respond(HttpStatusCode.OK, DataSuccess.User.UPDATED_SUCCESSFULLY)
        }
    }


}