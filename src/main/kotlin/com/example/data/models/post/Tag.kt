package com.example.data.models.post

import com.example.data.models.community.Community
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@kotlinx.serialization.Serializable
data class Tag(
    @BsonId
    val id: String = ObjectId().toString(),
    val communityID : String = "",
    val label: String = "",
    val intColor: Int = 0,
    val hexColor: String = "#000000",
)