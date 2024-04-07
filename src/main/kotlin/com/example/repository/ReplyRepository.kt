package com.example.repository

import com.example.data.models.Reply
import com.example.data.requests.ReplyRequest
import kotlinx.coroutines.flow.Flow

interface ReplyRepository {
    suspend fun createReply(request: ReplyRequest.CreateRequest): Boolean
    fun fetchReplies(request: ReplyRequest.FetchRequest): Flow<List<Reply>>
    suspend fun updateReply(request: ReplyRequest.UpdateRequest): Boolean
    suspend fun deleteReply(request: ReplyRequest.DeleteRequest): Boolean

    suspend fun upvoteReply(request: ReplyRequest.UpvoteRequest): Boolean
    suspend fun downvoteReply(request: ReplyRequest.DownvoteRequest): Boolean
    suspend fun reportReply(request: ReplyRequest.ReportRequest): Boolean
}