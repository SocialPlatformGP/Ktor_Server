package com.example.data.models.chat

import com.example.data.models.material.MessageAttachment
import com.example.data.models.post.now
import com.example.data.responses.ChatResponse
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
@kotlinx.serialization.Serializable
data class Message(
    val content :String,
    val createdAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).toEpochMilliseconds(),
    val roomId :String,
    val senderId :String,
    val hasAttachment: Boolean = false,
    val attachment: MessageAttachment = MessageAttachment(),
    @BsonId
    val id :String =ObjectId().toString()
){
    fun toResponse(senderName: String, senderPicUrl: String) = ChatResponse.MessageResponse(
        content = content,
        createdAt = createdAt,
        roomId = roomId,
        senderName = senderName,
        senderPfpURL = senderPicUrl,
        hasAttachment = hasAttachment,
        attachment = attachment,
        senderId = senderId,
        id = id
    )
}

