package com.example.data.requests

import com.example.data.models.assignment.Assignment
import com.example.data.models.assignment.AssignmentAttachment
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
    @Serializable
    data class GetAssignmentById(val assignmentId: String): AssignmentRequest
    @Serializable
    data class GetAssignmentSubmissions(val assignmentId: String): AssignmentRequest
    @Serializable
    data class SubmitReview(
        val submissionId: String,
        val grade: Int,
        val feedback: String
    ): AssignmentRequest
}