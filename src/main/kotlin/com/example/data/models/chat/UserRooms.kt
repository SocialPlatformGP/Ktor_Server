package com.example.data.models.chat

@kotlinx.serialization.Serializable
data class UserRooms(
    val userId :String,
    val recentRooms :List<String> //roomIds
)