package com.example.data.requests

import com.example.data.models.Reply
import kotlinx.serialization.Serializable

sealed class ReplyRequest{
    @Serializable
    data class CreateRequest(val reply: Reply): ReplyRequest()
    @Serializable
    data class FetchRequest(val postId: String): ReplyRequest()
    @Serializable
    data class UpdateRequest(val reply: Reply): ReplyRequest()
    @Serializable
    data class UpvoteRequest(val replyId: String, val userId: String): ReplyRequest()
    @Serializable
    data class DownvoteRequest(val replyId: String, val userId: String): ReplyRequest()
    @Serializable
    data class DeleteRequest(val replyId: String): ReplyRequest()
    @Serializable
    data class ReportRequest(val replyId: String, val reporterId: String): ReplyRequest()
}