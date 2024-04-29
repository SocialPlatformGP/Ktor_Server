package com.example.data.requests

import com.example.data.models.post.Tag

@kotlinx.serialization.Serializable
data class AddTagRequest(
    val label: String = "",
    val intColor: Int = 0,
    val hexColor: String = "#000000",
    val communityID: String = ""
){
    fun toEntity() = Tag(
        label = label,
        intColor = intColor,
        hexColor = hexColor,
        communityID = communityID
    )
}
