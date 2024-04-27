package com.example.routes.community

import com.example.repository.CommunityRepository
import com.example.routes.community.request.CommunityRequest
import com.example.utils.DataError
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
            ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val validCode = commRepo.checkCode(request)
        if (!validCode) {
            return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.INVALID_CODE)
        }
        val validDomain = commRepo.checkCommunityDomain(request)
        if (!validDomain) {
            return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.YOUR_EMAIL_DOMAIN_IS_NOT_ALLOWED)
        }
        val alreadyMember = commRepo.isAlreadyMember(request)
        if (alreadyMember) {
            return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.ALREADY_MEMBER)
        }
        val requestExist = commRepo.isRequestExist(request)
        if (requestExist) {
            return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.REQUEST_ALREADY_SENT)
        }
        val requireApproval = commRepo.isrequireApproval(request)
        val success = commRepo.joinCommunity(request)
        if (success && !requireApproval) {
            commRepo.getUserCommunities(request.id).let {
                call.respond(HttpStatusCode.OK, it)
            }
        } else if (success && requireApproval) {
                call.respond(HttpStatusCode.BadRequest, DataError.Network.WAIT_FOR_APPROVAL_FROM_ADMIN)
        }
    }
}