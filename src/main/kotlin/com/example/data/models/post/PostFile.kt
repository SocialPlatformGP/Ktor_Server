package com.example.data.models.post

@kotlinx.serialization.Serializable
data class PostFile(
    val file: ByteArray = byteArrayOf(),
    val url: String = "",
    val name: String = "",
    val type: String = "",
    val size: Long = 0
)