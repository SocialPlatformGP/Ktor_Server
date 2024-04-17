package com.example.repository

import com.example.data.models.post.Tag
import com.example.data.requests.UpdateOrDeletePostRequest
import com.example.data.responses.PostResponse

interface PostRepository {
    suspend fun createPost(postRequest: PostResponse): Boolean
    suspend fun updatePost(postRequest: PostResponse): Boolean
    suspend fun deletePost(postRequest: UpdateOrDeletePostRequest): Boolean
    suspend fun getPost(postRequest: UpdateOrDeletePostRequest): PostResponse
    suspend fun getPosts(): List<PostResponse>
    suspend fun upvotePost(postRequest: UpdateOrDeletePostRequest): PostResponse
    suspend fun downvotePost(postRequest: UpdateOrDeletePostRequest): PostResponse

    suspend fun insertTag(tag: Tag): Boolean
    suspend fun getTags(): List<Tag>
    suspend fun getNewPosts(request: Long): List<PostResponse>
}