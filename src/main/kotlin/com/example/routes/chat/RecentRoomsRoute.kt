package com.example.routes.chat

import com.example.data.requests.ChatRequest
import com.example.repository.MessageDataSource
import com.example.utils.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllRecentRooms(
    messageDataSource: MessageDataSource
) {
    post(EndPoint.Chat.RecentRooms.GetAllRecentRooms.route) {
        val request =
            call.receiveNullable<ChatRequest.GetAllRecentRooms>() ?: return@post call.respond(HttpStatusCode.BadRequest)
        println("\n Request: $request \n")
        val recentRooms = messageDataSource.getRecentRooms(request)
        println("\n Recent Rooms: $recentRooms \n")
        call.respond(HttpStatusCode.OK, recentRooms)
    }
}