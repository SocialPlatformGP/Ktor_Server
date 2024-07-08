package com.example.repository.reply

import com.example.data.models.post.Post
import com.example.data.models.post.now
import com.example.data.models.reply.Reply
import com.example.data.requests.ReplyRequest
import com.example.data.responses.ReplyResponse
import com.example.data.source.remote.ContentModerationRemoteDataSource
import com.example.utils.Constants
import com.example.utils.MimeType
import com.example.utils.ModerationSafety
import com.example.utils.inappropriateWords
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.litote.kmongo.addToSet
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.pull
import org.litote.kmongo.setValue

class ReplyRepositoryImpl(
    private val db: CoroutineDatabase,
    private val moderationRemoteSSource: ContentModerationRemoteDataSource,
) : ReplyRepository {
    private val replyCollection = db.getCollection<Reply>()
    private val postCollection = db.getCollection<Post>()

    override suspend fun createReply(response: ReplyResponse): Boolean {
        val post = postCollection.findOne(Post::id eq response.postId) ?: return false
        postCollection.updateOne(
            Post::id eq response.postId, setValue(
                Post::replyCount, post.replyCount + 1
            )
        )
        return replyCollection.insertOne(response.toEntity()).wasAcknowledged()
    }

    override suspend fun fetchReplies(request: ReplyRequest.FetchRequest): List<Reply> {
        val replies = replyCollection.find().toList().filter {
            it.postId == request.postId
        }
        return replies
    }

    override suspend fun updateReply(request: ReplyRequest.UpdateRequest): Boolean {
        val reply = replyCollection.findOne(Reply::id eq request.replyId) ?: return false
        return replyCollection.updateOne(Reply::id eq reply.id, reply.copy(content = request.replyContent))
            .wasAcknowledged()
    }

    override suspend fun deleteReply(request: ReplyRequest.DeleteRequest): Boolean {
        val reply = replyCollection.findOne(Reply::id eq request.replyId) ?: return false
        return replyCollection.deleteOne(Reply::id eq reply.id).wasAcknowledged()
    }

    override suspend fun upvoteReply(request: ReplyRequest.UpvoteRequest): Boolean {
        if (request.userId.isBlank()) {
            return false
        }
        val reply = replyCollection.findOne(Reply::id eq request.replyId) ?: return false
        val userAlreadyVoted = reply.upvoted.contains(request.userId)
        if (userAlreadyVoted) {
            replyCollection.updateOne(
                Reply::id eq request.replyId, pull(Reply::upvoted, request.userId)
            )
            replyCollection.updateOne(
                Reply::id eq request.replyId, setValue(
                    Reply::votes, reply.votes - 1
                )
            )
            return true
        } else {
            if (reply.downvoted.contains(request.userId)) {
                replyCollection.updateOne(
                    Reply::id eq request.replyId, pull(Reply::downvoted, request.userId)
                )
                replyCollection.updateOne(
                    Reply::id eq request.replyId, setValue(
                        Reply::votes, reply.votes + 2
                    )
                )
            } else {
                replyCollection.updateOne(
                    Reply::id eq request.replyId, setValue(
                        Reply::votes, reply.votes + 1
                    )
                )
            }
            replyCollection.updateOne(
                Reply::id eq request.replyId, addToSet(Reply::upvoted, request.userId)
            )
            return true
        }
    }

    override suspend fun downvoteReply(request: ReplyRequest.DownvoteRequest): Boolean {
        if (request.userId.isBlank()) {
            return false
        }
        val reply = replyCollection.findOne(Reply::id eq request.replyId) ?: return false
        val userAlreadyVoted = reply.downvoted.contains(request.userId)
        if (userAlreadyVoted) {
            replyCollection.updateOne(
                Reply::id eq request.replyId, pull(Reply::downvoted, request.userId)
            )
            replyCollection.updateOne(
                Reply::id eq request.replyId, setValue(
                    Reply::votes, reply.votes + 1
                )
            )
            return true
        } else {
            if (reply.upvoted.contains(request.userId)) {
                replyCollection.updateOne(
                    Reply::id eq request.replyId, pull(Reply::upvoted, request.userId)
                )
                replyCollection.updateOne(
                    Reply::id eq request.replyId, setValue(
                        Reply::votes, reply.votes - 2
                    )
                )
            } else {
                replyCollection.updateOne(
                    Reply::id eq request.replyId, setValue(
                        Reply::votes, reply.votes - 1
                    )
                )
            }
            replyCollection.updateOne(
                Reply::id eq request.replyId, addToSet(Reply::downvoted, request.userId)
            )
            return true
        }
    }

    override suspend fun reportReply(request: ReplyRequest.ReportRequest) {
        val reply = replyCollection.findOne(Reply::id eq request.replyId) ?: return
        println(reply)
        val titleResult = moderationRemoteSSource.validateText(reply.content)
        val titleLabel = titleResult?.result?.first()?.label
        val titleScore = titleResult?.result?.first()?.score
        println(titleResult)
        if (
            (titleLabel == "LABEL_1" && titleScore!! > 0.85)
            || reply.content.split(" ").any { it in inappropriateWords }
        ) {
            replyCollection.updateOne(
                Reply::id eq request.replyId,
                reply.copy(
                    moderationStatus = ModerationSafety.UNSAFE_REPLY.name,
                )
            )
        }
    }
}