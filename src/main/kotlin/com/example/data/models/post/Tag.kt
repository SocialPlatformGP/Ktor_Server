package com.example.data.models.post

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@kotlinx.serialization.Serializable
data class Tag(
    @BsonId
    val id: String = ObjectId().toString(),
    val label: String = "",
    val intColor: Int = 0,
    val hexColor: String = "#000000",
)