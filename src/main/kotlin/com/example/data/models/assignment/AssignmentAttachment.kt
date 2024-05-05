package com.example.data.models.assignment

import com.example.data.models.post.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class AssignmentAttachment (
    @BsonId
    val id : String = ObjectId().toString(),
    val byteArray: ByteArray = byteArrayOf(),
    val url: String = "",
    val name: String = "",
    val type: String = "",
    val size: Long = 0
)
@Serializable
data class UserAssignmentSubmission(
    @BsonId
    val id : String = ObjectId().toString(),
    val assignmentId: String,
    val userId: String,
    val attachments: List<AssignmentAttachment>,
    val submittedAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds,
    val grade: Int = 0,
    val isReviewed: Boolean = false,
    val isAccepted: Boolean = false,
    val isReturned: Boolean = false,
    val isTurnedIn: Boolean = false
    )
