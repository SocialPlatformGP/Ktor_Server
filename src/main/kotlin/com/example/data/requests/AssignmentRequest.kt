package com.example.data.requests

import com.example.data.models.assignment.Assignment
import com.example.data.models.assignment.AssignmentAttachment
import com.example.data.models.post.Post
import kotlinx.serialization.Serializable

sealed interface AssignmentRequest {
    @Serializable
    data class CreateRequest(val assignment: Assignment): AssignmentRequest
    @Serializable
    data class GetUserAttachmentsRequest(
        val userId: String,
        val assignmentId: String
    ): AssignmentRequest
    @Serializable
    data class SubmitRequest(
        val assignmentId: String,
        val userId: String,
        val attachments: List<AssignmentAttachment>
    ) : AssignmentRequest
}