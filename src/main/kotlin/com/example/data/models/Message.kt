package com.example.data.models

import com.example.data.responses.MessageResponse
import de.undercouch.bson4jackson.types.Timestamp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.json.JsonNull.content
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
@kotlinx.serialization.Serializable
data class Message(
    val content :String,
    val createdAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds,
    val roomId :String,
    val senderId :String,
    @BsonId
    val id :String =ObjectId().toString()
){
    fun toResponse(senderName: String, senderPicUrl: String) = MessageResponse(
        content = content,
        createdAt = createdAt,
        roomId = roomId,
        senderName = senderName,
        senderPicUrl = senderPicUrl,
        id = id
    )
}

