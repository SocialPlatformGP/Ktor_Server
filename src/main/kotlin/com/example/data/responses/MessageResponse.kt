package com.example.data.responses

@kotlinx.serialization.Serializable
data class MessageResponse(
    val content :String,
    val createdAt :Long,
    val roomId :String,
    val senderName: String,
    val senderPicUrl: String,
    val id :String
)