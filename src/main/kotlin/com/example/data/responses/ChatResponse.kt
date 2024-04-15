package com.example.data.responses

import com.example.data.models.RecentRoomResponse
import com.example.data.models.Room
import kotlinx.serialization.Serializable

sealed class ChatResponse {
    @kotlinx.serialization.Serializable
    data class CreateRoomResponse(
        val room: Room
    ) : ChatResponse()

    @kotlinx.serialization.Serializable
    data class GetRoomResponse(
        val room: Room
    ) : ChatResponse()

    @kotlinx.serialization.Serializable
    data class RoomExistResponse(
        val room: Room?
    ) : ChatResponse()

    @Serializable
    data class GetMessagesFromRoom(
        val messages: List<MessageResponse>
    ) : ChatResponse()

    @Serializable
    data class GetRecentRooms(
        val rooms: List<RecentRoomResponse>
    ) : ChatResponse()

    @kotlinx.serialization.Serializable
    data class MessageResponse(
        val content: String,
        val createdAt: Long,
        val roomId: String,
        val senderName: String,
        val senderPicUrl: String,
        val id: String
    ) : ChatResponse()

    @Serializable
    data class GetAllRecentRooms(
        val recentRooms: List<RecentRoomResponse>
    ) : ChatResponse()
    @Serializable
    data class CreateGroupRoom(
        val roomId: String
    ) : ChatResponse()
}