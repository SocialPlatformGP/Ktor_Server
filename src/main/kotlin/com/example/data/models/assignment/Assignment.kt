package com.example.data.models.assignment

import com.example.data.models.post.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Assignment(
    val title: String = "",
    val description: String = "",
    val id: String = ObjectId().toString(),
    val attachments: List<AssignmentAttachment> = emptyList(),
    val maxPoints: Int = 10,
    val dueDate: Long = 0,
    val acceptLateSubmissions: Boolean = false,
    val createdAt: Long = 0,
    val creatorId: String = "",
    val creatorName: String = "",
    val communityId: String = "",
) {

    fun toEntity(): AssignmentEntity {

        return AssignmentEntity(
            title = title,
            description = description,
            attachments = attachments,
            maxPoints = maxPoints,
            dueDate = dueDate,
            acceptLateSubmissions = acceptLateSubmissions,
            creatorId = creatorId,
            communityId = communityId
        ).copy(id = ObjectId().toString())
    }

}

@Serializable
data class AssignmentEntity(
    val title: String = "",
    val description: String = "",
    @BsonId
    val id: String = ObjectId().toString(),
    val attachments: List<AssignmentAttachment> = emptyList(),
    val maxPoints: Int = 10,
    val dueDate: Long = 0,
    val acceptLateSubmissions: Boolean = false,
    val createdAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).toEpochMilliseconds(),
    val creatorId: String = "",
    val creatorName: String = "",
    val communityId: String = "",
) {
    fun toModel() = Assignment(
        title = title,
        description = description,
        id = id,
        attachments = attachments,
        maxPoints = maxPoints,
        dueDate = dueDate,
        acceptLateSubmissions = acceptLateSubmissions,
        createdAt = createdAt,
        creatorId = creatorId,
        communityId = communityId,
        creatorName = creatorName
    )
}
