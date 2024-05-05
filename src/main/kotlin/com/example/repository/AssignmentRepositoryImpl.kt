package com.example.repository

import com.example.data.models.assignment.Assignment
import com.example.data.models.assignment.AssignmentAttachment
import com.example.data.models.assignment.AssignmentEntity
import com.example.data.models.assignment.UserAssignmentSubmission
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class AssignmentRepositoryImpl(db: CoroutineDatabase) : AssignmentRepository {
    private val assignments = db.getCollection<AssignmentEntity>()
    private val attachments = db.getCollection<AssignmentAttachment>()
    private val userAssignments = db.getCollection<UserAssignmentSubmission>()

    override suspend fun createAssignment(request: Assignment): Boolean {
        return assignments.insertOne(request.toEntity()).wasAcknowledged()
    }

    override suspend fun getAttachments(userId: String, assignmentId: String): List<AssignmentAttachment> {
        return attachments.find(
            UserAssignmentSubmission::assignmentId eq assignmentId,
            UserAssignmentSubmission::userId eq userId
        ).toList()
    }

    override suspend fun submitAssignment(
        assignmentId: String,
        userId: String,
        attachments: List<AssignmentAttachment>
    ): Boolean {
        val userAssignment = userAssignments.findOne(
            UserAssignmentSubmission::assignmentId eq assignmentId,
            UserAssignmentSubmission::userId eq userId
        )
        return if (userAssignment != null) {
            userAssignments.updateOne(
                UserAssignmentSubmission::id eq userAssignment.id,
                userAssignment.copy(attachments = userAssignment.attachments + attachments)
            ).wasAcknowledged()
        } else {
            userAssignments.insertOne(
                UserAssignmentSubmission(
                    assignmentId = assignmentId,
                    userId = userId,
                    attachments = attachments
                )
            ).wasAcknowledged()
        }
    }

    override suspend fun getAssignments(communityId: String): List<Assignment> {
        return assignments.find(AssignmentEntity::communityId eq communityId).toList().map { it.toModel() }
    }
}