package com.example.routes.community

import com.example.repository.community.CommunityRepository
import com.example.routes.community.request.CommunityRequest
import com.example.utils.CommunityError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.joinCommunity(
    commRepo: CommunityRepository
) {
    post("/joinCommunity") {
        val request = call.receiveNullable<CommunityRequest.JoinCommunity>()
            ?: return@post call.respond(HttpStatusCode.BadRequest, CommunityError.SERVER_ERROR)
        val validCode = commRepo.checkCode(request)
        if (!validCode) {
            return@post call.respond(HttpStatusCode.BadRequest, CommunityError.SERVER_ERROR)
        }
        val validDomain = commRepo.checkCommunityDomain(request)
        if (!validDomain) {
            return@post call.respond(HttpStatusCode.BadRequest, CommunityError.SERVER_ERROR)
        }
        val alreadyMember = commRepo.isAlreadyMember(request)
        if (alreadyMember) {
            return@post call.respond(HttpStatusCode.BadRequest, CommunityError.SERVER_ERROR)
        }
        val requestExist = commRepo.isRequestExist(request)
        if (requestExist) {
            return@post call.respond(HttpStatusCode.BadRequest, CommunityError.SERVER_ERROR)
        }
        val requireApproval = commRepo.isrequireApproval(request)
        val success = commRepo.joinCommunity(request)
        if (success && !requireApproval) {
            commRepo.getUserCommunities(request.id).let {
                call.respond(HttpStatusCode.OK, it)
            }
        } else if (success && requireApproval) {
                call.respond(HttpStatusCode.BadRequest, CommunityError.SERVER_ERROR)
        }
    }
}