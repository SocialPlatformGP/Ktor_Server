package com.example.data.responses

import com.example.data.models.Room
import kotlinx.serialization.Serializable

sealed class ChatResponse {
    @Serializable
    data class CheckRoomExists(
        val room: Room
    ) : ChatResponse()

    @Serializable
    data class GetMessagesFromRoom(
        val messages: List<MessageResponse>
    ) : ChatResponse()
}