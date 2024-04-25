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