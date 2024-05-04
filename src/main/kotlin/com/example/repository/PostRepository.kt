package com.example.repository

import com.example.data.models.post.Tag
import com.example.data.requests.PostRequest
import com.example.data.requests.ReplyRequest
import com.example.data.requests.UpdateOrDeletePostRequest
import com.example.data.responses.PostResponse

interface PostRepository {
    suspend fun createPost(postRequest: PostResponse): Boolean
    suspend fun updatePost(postRequest: PostRequest.UpdateRequest): Boolean
    suspend fun deletePost(postRequest: UpdateOrDeletePostRequest): Boolean
    suspend fun getPost(postRequest: UpdateOrDeletePostRequest): PostResponse
    suspend fun getPosts(): List<PostResponse>
    suspend fun upvotePost(postRequest: UpdateOrDeletePostRequest): PostResponse
    suspend fun downvotePost(postRequest: UpdateOrDeletePostRequest): PostResponse
    suspend fun reportPost(request: PostRequest.ReportRequest): Boolean
    suspend fun insertTag(tag: Tag): Boolean
    suspend fun getTags(request: String): List<Tag>
    suspend fun getNewPosts(request: Long): List<PostResponse>
    suspend fun getUserPosts(request: String): List<PostResponse>
}