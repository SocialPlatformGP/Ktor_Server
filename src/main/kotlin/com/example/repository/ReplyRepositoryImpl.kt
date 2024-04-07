package com.example.repository

import com.example.data.models.Reply
import com.example.data.models.Tag
import com.example.data.requests.ReplyRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.litote.kmongo.coroutine.CoroutineDatabase

class ReplyRepositoryImpl(db: CoroutineDatabase) : ReplyRepository {
    private val replyCollection = db.getCollection<Reply>()

    override suspend fun createReply(request: ReplyRequest.CreateRequest): Boolean {
        return replyCollection.insertOne(request.reply).wasAcknowledged()
    }

    override fun fetchReplies(request: ReplyRequest.FetchRequest): Flow<List<Reply>> = flow {
        val replies = replyCollection.find().toList().filter{
            it.postId == request.postId
        }
        emit(replies)
    }

    override suspend fun updateReply(request: ReplyRequest.UpdateRequest): Boolean {
        val reply = replyCollection.findOneById(request.reply.id) ?: return false
        return replyCollection.updateOneById(reply.id, request.reply).wasAcknowledged()
    }

    override suspend fun deleteReply(request: ReplyRequest.DeleteRequest): Boolean {
        val reply = replyCollection.findOneById(request.replyId) ?: return false
        return replyCollection.deleteOneById(reply.id).wasAcknowledged()
    }

    override suspend fun upvoteReply(request: ReplyRequest.UpvoteRequest): Boolean {
        val reply = replyCollection.findOneById(request.replyId)?: return false
        val userAlreadyVoted = reply.upvoted.contains(request.userId)
        return if (userAlreadyVoted) {
            false
        } else {
            replyCollection.updateOneById(reply.id, reply.copy(votes = reply.votes + 1, upvoted = reply.upvoted + request.userId)).wasAcknowledged()
        }
    }

    override suspend fun downvoteReply(request: ReplyRequest.DownvoteRequest): Boolean {
        val reply = replyCollection.findOneById(request.replyId)?: return false
        val userAlreadyVoted = reply.downvoted.contains(request.userId)
        return if (userAlreadyVoted) {
            false
        } else {
            replyCollection.updateOneById(reply.id, reply.copy(votes = reply.votes - 1, upvoted = reply.downvoted + request.userId)).wasAcknowledged()
        }    }

    override suspend fun reportReply(request: ReplyRequest.ReportRequest): Boolean {
        return true
    }
}