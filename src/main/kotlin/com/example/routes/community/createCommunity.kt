package com.example.routes.community

import com.example.repository.CommunityRepository
import com.example.routes.community.request.CommunityRequest
import com.example.utils.DataError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*



fun Route.createCommunity(
    commRepo: CommunityRepository
) {
    post("/createCommunity") {
        val request = call.receiveNullable<CommunityRequest.CreateCommunity>()?: return@post call.respond(HttpStatusCode.BadRequest,DataError.Network.BAD_REQUEST)
        val community = commRepo.createCommunity(request)
        call.respond(HttpStatusCode.OK, community)

    }
}
fun Route.joinCommunity(
    commRepo: CommunityRepository
) {
    post("/joinCommunity") {
        val request = call.receiveNullable<CommunityRequest.JoinCommunity>()?: return@post call.respond(HttpStatusCode.BadRequest,DataError.Network.BAD_REQUEST)
        val validDomain = commRepo.checkCommunityDomain(request)
        if (!validDomain) {
            return@post call.respond(HttpStatusCode.BadRequest,DataError.Network.YOUR_EMAIL_DOMAIN_IS_NOT_ALLOWED)
        }
        val community = commRepo.joinCommunity(request)
        if(community.isEmpty()){
            return@post call.respond(HttpStatusCode.BadRequest,DataError.Network.INVALID_CODE)
        }
        call.respond(HttpStatusCode.OK, community)

    }
}