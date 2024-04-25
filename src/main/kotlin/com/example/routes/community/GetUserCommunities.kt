package com.example.routes.community

import com.example.data.models.community.Community
import com.example.data.models.community.UserCommunities
import com.example.repository.CommunityRepository
import com.example.routes.community.request.CommunityRequest
import com.example.utils.DataError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUserCommunities(
    commRepo: CommunityRepository
){
    post("/getUserCommunities") {
        val userId = call.receiveNullable<String>()?: return@post call.respond(HttpStatusCode.BadRequest,DataError.Network.BAD_REQUEST)
        val result = commRepo.getUserCommunities(userId)
        call.respond(HttpStatusCode.OK,result)
    }
}

fun Route.communityLogout(
    commRepo: CommunityRepository
){
    post("/communityLogout") {
        val request = call.receiveNullable<CommunityRequest.LogoutCommunity>()?: return@post call.respond(HttpStatusCode.BadRequest,DataError.Network.BAD_REQUEST)
        val result = commRepo.logoutCommunity(request)
        call.respond(HttpStatusCode.OK,result)
    }
}

