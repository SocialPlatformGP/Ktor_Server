package com.example.data.models.material

import kotlinx.serialization.Serializable

@Serializable
data class MessageAttachment(
    val byteArray: ByteArray = byteArrayOf(),
    val url: String = "",
    val name: String = "",
    val type: String = "",
    val size: Long = 0
)