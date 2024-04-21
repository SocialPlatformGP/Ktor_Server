package com.example.data.responses

import com.example.data.models.reply.Reply

data class ReplyResponse(
    val id: String = "",
    val authorID: String = "",
    val postId: String = "",
    val parentReplyId: String? = "",
    val content: String = "",
    val votes: Int = 0,
    val depth: Int = 0,
    val createdAt: Long = 0,
    val deleted: Boolean = false,
    val upvoted: List<String> = emptyList(),
    val downvoted: List<String> = emptyList(),
    val authorName: String = "",
    val authorImageLink: String = "",
    val collapsed: Boolean = false,
    val editStatus: Boolean = false
) {
    fun toEntity(): Reply = Reply(
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
        editStatus = editStatus
    )
}
