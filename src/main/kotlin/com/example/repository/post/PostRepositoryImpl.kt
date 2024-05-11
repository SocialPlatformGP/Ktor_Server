package com.example.repository.post

import com.example.data.models.post.Post
import com.example.data.models.post.Tag
import com.example.data.models.post.now
import com.example.data.models.user.User
import com.example.data.requests.PostRequest
import com.example.data.requests.UpdateOrDeletePostRequest
import com.example.data.responses.PostResponse
import com.example.data.source.remote.ContentModerationRemoteDataSource
import com.example.utils.Constants
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase

class PostRepositoryImpl(
    private val db: CoroutineDatabase,
    private val moderationRemoteSSource: ContentModerationRemoteDataSource
) : PostRepository {
    private val postCollection = db.getCollection<Post>()
    private val userCollection = db.getCollection<User>()
    private val tagCollection = db.getCollection<Tag>()
    override suspend fun createPost(postRequest: PostResponse): Boolean {
        println("*************************************\ncreate post called: $postRequest\n**********************")
        return postCollection.insertOne(postRequest.toEntity()).wasAcknowledged()

    }

    override suspend fun reportPost(request: PostRequest.ReportRequest): Boolean {
        val post = postCollection.findOne(Post::id eq request.postId) ?: return false
        val titleResult = moderationRemoteSSource.validateText(post.title)
        println("\n\n\n\n\n\ntitle result: $titleResult\n\n\n\n\n\n")
        val bodyResult = moderationRemoteSSource.validateText(post.body)
        println("\n\n\n\n\n\nbody result: $bodyResult\n\n\n\n\n\n")
        post.attachments.filter { it.type == "image" }.forEach {
            val imageResult = moderationRemoteSSource.validateImage("${ Constants.BASE_URL }${it.url}")
            println("\n\n\n\n\n\nimage ${it.name} result: $imageResult\n\n\n\n\n\n")
        }
        return true
    }

    override suspend fun updatePost(postRequest: PostRequest.UpdateRequest): Boolean {
        postCollection.findOne(Post::id eq postRequest.post.id) ?: return false
        val post = postRequest.post.copy(lastModified = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds)
        return postCollection.updateOne(Post::id eq postRequest.post.id, post).wasAcknowledged()
    }

    override suspend fun deletePost(postRequest: UpdateOrDeletePostRequest): Boolean {
        return postCollection.deleteOne(Post::id eq postRequest.postId).wasAcknowledged()
    }

    override suspend fun getPost(postRequest: UpdateOrDeletePostRequest): PostResponse {
        val post = postCollection.findOne(Post::id eq postRequest.postId)
        if (post != null) {
            val user = userCollection.findOneById(post.authorID) ?: User()
            return post.toResponse()
        } else {
            return PostResponse()
        }

    }

    override suspend fun getPosts(): List<PostResponse> = postCollection.find().toList()
        .map {
            it.toResponse()
        }

    override suspend fun upvotePost(postRequest: UpdateOrDeletePostRequest): PostResponse {
        println(postRequest.postId)
        println(postRequest.userId.isBlank())
        if (postRequest.userId.isBlank()) {
            return PostResponse()
        }
        val post = postCollection.findOne(Post::id eq postRequest.postId)?: return PostResponse()
        println(post)
        val userAlreadyVoted = post.upvoted.contains(postRequest.userId)
        println(userAlreadyVoted)
        if (userAlreadyVoted) {
            postCollection.updateOne(
                Post::id eq postRequest.postId,
                pull(Post::upvoted, postRequest.userId)
            )
            postCollection.updateOne(
                Post::id eq postRequest.postId,
                setValue(
                    Post::votes, post.votes - 1
                )
            )
            postCollection.updateOne(
                Post::id eq postRequest.postId,
                setValue(Post::lastModified, LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds)
            )
            return post.toResponse()
        } else {

            if (post.downvoted.contains(postRequest.userId)) {
                postCollection.updateOne(
                    Post::id eq postRequest.postId,
                    pull(Post::downvoted, postRequest.userId)
                )
                postCollection.updateOne(
                    Post::id eq postRequest.postId,
                    setValue(
                        Post::votes, post.votes + 2
                    )
                )
            }else {
                postCollection.updateOne(
                    Post::id eq postRequest.postId,
                    setValue(
                        Post::votes, post.votes + 1
                    )
                )
            }
            postCollection.updateOne(
                Post::id eq postRequest.postId,
                addToSet(Post::upvoted, postRequest.userId)
            )
            postCollection.updateOne(
                Post::id eq postRequest.postId,
                setValue(Post::lastModified, LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds)
            )
            return post.toResponse()
        }
    }

    override suspend fun downvotePost(postRequest: UpdateOrDeletePostRequest): PostResponse {
        println(postRequest.postId)
        if (postRequest.userId.isBlank()) {
            return PostResponse()
        }
        val post = postCollection.findOne(Post::id eq postRequest.postId)?: return PostResponse()
        println(post)
        val userAlreadyVoted = post.downvoted.contains(postRequest.userId)
        println(userAlreadyVoted)
        if (userAlreadyVoted) {
            postCollection.updateOne(
                Post::id eq postRequest.postId,
                pull(Post::downvoted, postRequest.userId)
            )
            postCollection.updateOne(
                Post::id eq postRequest.postId,
                setValue(
                    Post::votes, post.votes + 1
                )
            )
            postCollection.updateOne(
                Post::id eq postRequest.postId,
                setValue(Post::lastModified, LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds)
            )
            return post.toResponse()
        } else {
            if (post.upvoted.contains(postRequest.userId)) {
                postCollection.updateOne(
                    Post::id eq postRequest.postId,
                    pull(Post::upvoted, postRequest.userId)
                )
                postCollection.updateOne(
                    Post::id eq postRequest.postId,
                    setValue(
                        Post::votes, post.votes - 2
                    )
                )
            }
            else {
                postCollection.updateOne(
                    Post::id eq postRequest.postId,
                    setValue(
                        Post::votes, post.votes - 1
                    )
                )
            }
            postCollection.updateOne(
                Post::id eq postRequest.postId,
                addToSet(Post::downvoted, postRequest.userId)
            )
            postCollection.updateOne(
                Post::id eq postRequest.postId,
                setValue(Post::lastModified, LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds)
            )
            return post.toResponse()
        }
    }

    override suspend fun insertTag(tag: Tag): Boolean {
        val foundTag = tagCollection.findOne(Tag::label eq tag.label)
        if (foundTag != null) {
            return true
        }
        return tagCollection.insertOne(tag).wasAcknowledged()
    }

    override suspend fun getUserPosts(request: String): List<PostResponse> {
        return postCollection.find(Post::authorID eq request).toList().map {
            it.toResponse()
        }
    }

    override suspend fun getTags(request: String): List<Tag> {
        return tagCollection.find(Tag::communityID eq request).toList()
    }

    override suspend fun getNewPosts(request: Long): List<PostResponse> {
        return postCollection.find(Post::lastModified gt request).toList().map {
            it.toResponse()
        }
    }


}