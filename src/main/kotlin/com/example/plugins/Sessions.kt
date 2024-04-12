package com.example.plugins

import com.example.utils.Constants.ROOM_ID
import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.sessions.*
import io.ktor.util.*

fun Application.configureSession2() {
    install(Sessions) {
        cookie<ChatSession>("SESSION")
    }
    intercept(Plugins) {
        if (call.sessions.get<ChatSession>() == null) {
            val username = call.parameters["username"]?: "unknown_user"
            val roomId = call.parameters["room_id"]?: "unknown_room"
            call.sessions.set(
                ChatSession(
                    username,
                    generateNonce(),
                    roomId
                ))
        }
    }
}
data class ChatSession(
    val userName: String,
    val sessionId :String,
    val roomId :String
    )
