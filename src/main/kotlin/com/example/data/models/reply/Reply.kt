package com.example.data.models.reply

import com.example.data.models.post.now
import com.example.data.responses.ReplyResponse
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Reply (
    @BsonId
    val id: String = ObjectId().toString(),
    val authorID: String = "",
    val postId: String = "",
    val parentReplyId: String? = "",
    val content: String = "",
    val votes: Int = 0,
    val depth: Int = 0,
    val createdAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).toEpochMilliseconds(),
    val deleted: Boolean = false,
    val upvoted: List<String> = emptyList(),
    val downvoted: List<String> = emptyList(),
    val authorName: String = "",
    val authorImageLink: String = "",
    val collapsed: Boolean = false,
    val editStatus: Boolean = false,
    val moderationStatus: String = "SAFE"
){
    fun toResponse(): ReplyResponse = ReplyResponse(
        id = id,
        authorID = authorID,
        postId = postId,
        parentReplyId = parentReplyId,
        content = content,
        votes = votes,
        depth = depth,
        createdAt = createdAt,
        deleted = deleted,
        upvoted = upvoted,
        downvoted = downvoted,
        authorName = authorName,
        authorImageLink = authorImageLink,
        collapsed = collapsed,
        editStatus = editStatus,
        moderationStatus = moderationStatus
    )
}