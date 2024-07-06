package com.example.repository.assignment

import com.example.data.models.assignment.Assignment
import com.example.data.models.assignment.AssignmentAttachment
import com.example.data.models.assignment.UserAssignmentSubmission
import com.example.data.requests.AssignmentRequest

interface AssignmentRepository {
    suspend fun createAssignment(request: Assignment): Boolean
    suspend fun getAttachments(userId: String, assignmentId: String): UserAssignmentSubmission?
    suspend fun submitAssignment(
        assignmentId: String,
        userId: String,
        attachments: List<AssignmentAttachment>
    ): Boolean

    suspend fun getAssignments(communityId: String): List<Assignment>
    suspend fun getAssignment(request: String): Assignment?
    suspend fun getSubmissions(request: String): List<UserAssignmentSubmission>
    suspend fun submitReview(request: AssignmentRequest.SubmitReview): Boolean
    suspend fun turnInAssignment(request: String): Boolean
    suspend fun unSubmitAssignment(request: String): Boolean
    suspend fun canSubmit(request: String): Boolean
}