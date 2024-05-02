package com.example.data.models.user

@kotlinx.serialization.Serializable
data class User(
    val id: String = "",
    val name: String = "",
    val profilePictureURL: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birthdate: Long = 0L,
    val bio: String = "",
    val createdAt: Long = 0L,
    val isAdmin: Boolean = false,
    val isDataComplete: Boolean = false,
)