package com.example.data.responses

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MaterialFolderResponse(
    val name: String="",
    val createdAt: LocalDateTime? = null,
    val id : String = "",
    val path: String = ""
)
