package com.example.routes.community

import com.example.repository.community.CommunityRepository
import com.example.routes.community.request.CommunityRequest
import com.example.utils.CalendarError
import com.example.utils.CommunityError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.getUserCommunities(
    commRepo: CommunityRepository
){
    post("/getUserCommunities") {
        val userId = call.receiveNullable<String>()?: return@post call.respond(HttpStatusCode.BadRequest,CalendarError.SERVER_ERROR)
        val result = commRepo.getUserCommunities(userId)
        println("\n\n\n\n\n\\User communities: $result\n\n\n\n\n")
        call.respond(HttpStatusCode.OK,result)
    }
}

fun Route.communityLogout(
    commRepo: CommunityRepository
){
    post("/communityLogout") {
        val request = call.receiveNullable<CommunityRequest.LogoutCommunity>()?: return@post call.respond(HttpStatusCode.BadRequest,CommunityError.SERVER_ERROR)
        val result = commRepo.logoutCommunity(request)
        call.respond(HttpStatusCode.OK,result)
    }
}

