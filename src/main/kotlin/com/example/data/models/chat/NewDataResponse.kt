package com.example.data.models.chat

import com.example.data.responses.ChatResponse

@kotlinx.serialization.Serializable
data class NewDataResponse(
    val messages: ChatResponse.MessageResponse? = null,
    val recentRooms: List<RecentRoomResponse> = emptyList()
)