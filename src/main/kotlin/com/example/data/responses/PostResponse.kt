package com.example.data.responses

import com.example.data.models.post.Post
import com.example.data.models.post.PostFile
import com.example.data.models.post.Tag

@kotlinx.serialization.Serializable
data class PostResponse(
    val replyCount: Int = 0,
    val authorName: String = "",
    val authorPfp: String = "",
    val id: String = "",
    val authorID: String = "",
    val createdAt: Long=0L,
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
    val communityId: String = "",
    val lastModified: Long = 0L
    ){
    fun toEntity() = Post(
        replyCount = replyCount,
        authorName = authorName,
        authorPfp = authorPfp,
        authorID = authorID,
        title = title,
        body = body,
        votes = votes,
        downvoted = downvoted,
        upvoted = upvoted,
        moderationStatus = moderationStatus,
        editedStatus = editedStatus,
        tags = tags,
        type = type,
        attachments = attachments,
        communityId = communityId,
    )
}
