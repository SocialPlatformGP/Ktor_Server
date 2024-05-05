package com.example.repository

import com.example.data.models.assignment.Assignment
import com.example.data.models.assignment.AssignmentAttachment
import kotlinx.coroutines.flow.Flow

interface AssignmentRepository {
    suspend fun createAssignment(request: Assignment): Boolean
    suspend fun getAttachments(userId: String, assignmentId: String): List<AssignmentAttachment>
    suspend fun submitAssignment(
        assignmentId: String,
        userId: String,
        attachments: List<AssignmentAttachment>
    ): Boolean

    suspend fun getAssignments(communityId: String): List<Assignment>
}