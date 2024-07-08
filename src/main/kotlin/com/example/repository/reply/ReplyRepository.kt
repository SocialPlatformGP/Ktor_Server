package com.example.repository.reply

import com.example.data.models.reply.Reply
import com.example.data.requests.ReplyRequest
import com.example.data.responses.ReplyResponse

interface ReplyRepository {
    suspend fun createReply(response: ReplyResponse): Boolean
    suspend fun fetchReplies(request: ReplyRequest.FetchRequest): List<Reply>
    suspend fun updateReply(request: ReplyRequest.UpdateRequest): Boolean
    suspend fun deleteReply(request: ReplyRequest.DeleteRequest): Boolean

    suspend fun upvoteReply(request: ReplyRequest.UpvoteRequest): Boolean
    suspend fun downvoteReply(request: ReplyRequest.DownvoteRequest): Boolean
    suspend fun reportReply(request: ReplyRequest.ReportRequest)
}