package com.example.data.requests

import com.example.data.models.assignment.Assignment
import com.example.data.models.post.Post
import kotlinx.serialization.Serializable

sealed interface AssignmentRequest {
    @Serializable
    data class CreateRequest(val assignment: Assignment): AssignmentRequest
}