package com.example.data.models.chat

@kotlinx.serialization.Serializable
data class RecentRoomResponse(
    val roomId: String,
    val lastMessage: String,
    val isPrivate: Boolean,
    val senderName: String,
    val pic_url: String, //if the room is a group chat, this will be the group chat image
    val title :String, //if the room is a group chat, this will be the group chat name
    val lastMessageTime: Long,
)