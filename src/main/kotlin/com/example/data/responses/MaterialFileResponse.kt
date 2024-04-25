package com.example.data.responses

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MaterialFileResponse(
    val name: String="",
    val type: String="",
    val url: String="",
    val createdAt: LocalDateTime? = null,
    val id : String ="",
    val path : String = "",
    val localPath: String = "",
    val communityId: String=""
)