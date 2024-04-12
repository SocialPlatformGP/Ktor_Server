package com.example.data.requests

import kotlinx.serialization.Serializable

sealed class ChatRequest {
    @Serializable
    data class CheckRoomExists(
        val sender: String,
        val receiver: String
    ) : ChatRequest()

    @Serializable
    data class GetMessagesFromRoom(
        val roomId: String
    ) : ChatRequest()
}