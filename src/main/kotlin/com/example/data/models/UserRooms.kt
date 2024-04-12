package com.example.data.models

import org.bson.codecs.pojo.annotations.BsonId
@kotlinx.serialization.Serializable
data class UserRooms(
    val userId :String,
    val recentRooms :List<String> //roomIds
)