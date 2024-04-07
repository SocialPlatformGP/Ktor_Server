package com.example.data.requests

@kotlinx.serialization.Serializable
data class UpdateOrDeletePostRequest(
    val postId: String = "",
    val userId: String = "",
)
