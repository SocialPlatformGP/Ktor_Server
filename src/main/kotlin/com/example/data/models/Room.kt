package com.example.data.models

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
@kotlinx.serialization.Serializable
data class Room(
    @BsonId
    val id: String = ObjectId().toString(),
    val name: String,
    val picUrl: String,
    val members: Map<String,Boolean>, //userId to isAdmin
    val isPrivate: Boolean,
    val createdAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds,
    val bio: String, //group chat description
){
    fun newRecentRoom() = RecentRoom(
        roomId = id,
        title = name,
        pic_url = picUrl,
        lastMessage = "",
        lastMessageTime = createdAt,
        isPrivate = isPrivate,
        sender_id = "",
        receiver_id = "",
    )
}