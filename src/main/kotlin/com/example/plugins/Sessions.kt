package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.sessions.*
import io.ktor.util.*

fun Application.configureSession() {
    install(Sessions) {
        cookie<ChatSession>("SESSION")
    }
    intercept(Plugins) {
        if (call.sessions.get<ChatSession>() == null) {
            val userId = call.parameters["userid"] ?: "unknown_user"
            call.sessions.set(
                ChatSession(
                    userId,
                    generateNonce(),
                )
            )
            println("Session created for $userId")
        }
    }
}

data class ChatSession(
    val userId: String,
    val sessionId: String,
)
