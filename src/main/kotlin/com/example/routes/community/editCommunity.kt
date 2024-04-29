package com.example.routes.community

import com.example.repository.CommunityRepository
import com.example.routes.community.request.CommunityRequest
import com.example.utils.DataError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.editCommunity(
 commReo: CommunityRepository
) {
    post("editCommunity") {
        val request = call.receiveNullable<CommunityRequest.EditCommunity>() ?: return@post call.respond(HttpStatusCode.BadRequest, DataError.Network.BAD_REQUEST)
        val result = commReo.editCommunity(request)
        if (result) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.BadRequest, DataError.Network.NOT_FOUND)
        }
    }
}