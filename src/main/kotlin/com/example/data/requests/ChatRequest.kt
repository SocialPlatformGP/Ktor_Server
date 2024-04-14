package com.example.data.requests

import com.example.data.models.Room
import kotlinx.serialization.Serializable

sealed class ChatRequest {
    @Serializable
    data class CreateRoomRequest(
        val room: Room
    ) : ChatRequest()

    @Serializable
    data class GetRoomRequest(
        val roomId: String,
    ) : ChatRequest()

    @Serializable
    data class RoomExistRequest(
        val senderId: String,
        val receiverId: String,
    ) : ChatRequest()

    @Serializable
    data class GetMessagesFromRoom(
        val roomId: String
    ) : ChatRequest()

    @Serializable
    data class GetAllRecentRooms(
        val userId: String
    ) : ChatRequest()

    @Serializable
    data class CreateGroupRoom(
        val groupName: String,
        val groupAvatar: ByteArray,
        val userIds: List<String>,
        val creatorId: String
    ) : ChatRequest()
}