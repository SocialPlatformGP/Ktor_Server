package com.example.data.models.assignment

import kotlinx.serialization.Serializable

@Serializable
data class AssignmentAttachment (
    val byteArray: ByteArray = byteArrayOf(),
    val url: String = "",
    val name: String = "",
    val type: String = "",
    val size: Long = 0
)
