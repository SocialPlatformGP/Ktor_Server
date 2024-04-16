package com.example.plugins

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
            val userId = call.parameters["userid"] ?: "unknown_user"
            val roomId = call.parameters["roomid"] ?: "unknown_room"
            call.sessions.set(
                ChatSession(
                    userId,
                    generateNonce(),
                    roomId
                )
            )
            println("Session created for $userId")
        }
    }
}

data class ChatSession(
    val userId: String,
    val sessionId: String,
    val roomId: String
)
