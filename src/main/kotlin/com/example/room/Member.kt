package com.example.room

import io.ktor.server.websocket.*
import io.ktor.websocket.*

data class Member(
    val userId :String,
    val sessionId:String,
    val roomId :String,
    val socket :DefaultWebSocketServerSession
)
