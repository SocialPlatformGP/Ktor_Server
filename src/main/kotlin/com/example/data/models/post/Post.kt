package com.example.data.models.post

import com.example.data.models.user.User
import com.example.data.responses.PostResponse
import kotlinx.datetime.*

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
fun LocalDateTime.Companion.now() = Clock.System.now().toLocalDateTime(TimeZone.UTC)


@kotlinx.serialization.Serializable
data class Post(
    val replyCount: Int = 0,
    val authorName: String = "",
    val authorPfp: String = "",
    val authorID: String = "",
    val createdAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds,
    val title: String = "",
    val body: String = "",
    val votes: Int = 0,
    val downvoted: List<String> = emptyList(),
    val upvoted: List<String> = emptyList(),
    val moderationStatus: String = "submitted",
    val editedStatus: Boolean = false,
    val tags: List<Tag> = emptyList(),
    val type: String = "general",
    val attachments: List<PostFile> = emptyList(),
    val lastModified: Long = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds,
    val communityId: String = "",
    @BsonId
    val id: String = ObjectId().toString(),

    ) {
    var user: User = User()
    fun toResponse() = PostResponse(
        id = id,
        title = title,
        body = body,
        tags = tags,
        attachments = attachments,
        createdAt = createdAt,
        authorName = authorName,
        authorPfp = authorPfp,
        authorID = authorID,
        moderationStatus = moderationStatus,
        editedStatus = editedStatus,
        type = type,
        votes = votes,
        downvoted = downvoted,
        upvoted = upvoted,
        replyCount = replyCount,
        communityId = communityId,
        lastModified = lastModified


    )
}







