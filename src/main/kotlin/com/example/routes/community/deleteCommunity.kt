package com.example.routes.community

import com.example.repository.community.CommunityRepository
import com.example.routes.community.request.CommunityRequest
import com.example.utils.CommunityError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteCommunity(
    communityRepository: CommunityRepository
) {
    post("deleteCommunity") {
        val request = call.receiveNullable<CommunityRequest.DeleteCommunity>() ?: return@post call.respond(
            HttpStatusCode.BadRequest,
            CommunityError.SERVER_ERROR
        )
        val result = communityRepository.deleteCommunity(request)
        if (result) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.BadRequest, CommunityError.SERVER_ERROR)
        }
    }
}