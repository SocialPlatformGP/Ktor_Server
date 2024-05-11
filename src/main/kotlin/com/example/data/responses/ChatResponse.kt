package com.example.data.responses

import com.example.data.models.chat.RecentRoomResponse
import com.example.data.models.chat.Room
import com.example.data.models.material.MessageAttachment
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

    @Serializable
    data class MessageResponse(
        val content: String = "",
        val createdAt: Long =0L,
        val roomId: String = "",
        val senderId: String = "",
        val id: String = "",
        val senderName: String = "",
        val senderPfpURL: String = "",
        val hasAttachment: Boolean = false,
        val attachment: MessageAttachment = MessageAttachment()
    ) : ChatResponse()

    @Serializable
    data class GetAllRecentRooms(
        val recentRooms: List<RecentRoomResponse>
    ) : ChatResponse()
    @Serializable
    data class CreateGroupRoom(
        val roomId: String,
        val roomAvatarUrl: String,
    ) : ChatResponse()
@Serializable
    data class GetRoomDetails (
        val room: Room
    ) : ChatResponse()
@Serializable
    data class UpdateRoomAvatar (
        val avatarUrl: String
    ) : ChatResponse()
}