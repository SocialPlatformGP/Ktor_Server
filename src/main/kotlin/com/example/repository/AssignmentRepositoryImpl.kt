package com.example.repository

import com.example.data.models.assignment.Assignment
import com.example.data.models.assignment.AssignmentAttachment
import com.example.data.models.assignment.AssignmentEntity
import com.example.data.models.assignment.UserAssignmentSubmission
import com.example.data.models.user.User
import com.example.data.requests.AssignmentRequest
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class AssignmentRepositoryImpl(db: CoroutineDatabase) : AssignmentRepository {
    private val assignments = db.getCollection<AssignmentEntity>()
    private val userAssignments = db.getCollection<UserAssignmentSubmission>()
    private val users = db.getCollection<User>()

    override suspend fun createAssignment(request: Assignment): Boolean {
        val user = users.findOne(User::id eq request.creatorId)
        return assignments.insertOne(request.copy(creatorName = user?.name ?: "").toEntity()).wasAcknowledged()
    }

    override suspend fun getAttachments(userId: String, assignmentId: String): UserAssignmentSubmission? {
        return userAssignments.findOne(
            UserAssignmentSubmission::assignmentId eq assignmentId,
            UserAssignmentSubmission::userId eq userId
        )
    }

    override suspend fun getAssignment(request: String): Assignment? {
        return assignments.findOne(AssignmentEntity::id eq request)?.toModel()
    }

    override suspend fun getSubmissions(request: String): List<UserAssignmentSubmission> {
        return userAssignments.find(UserAssignmentSubmission::assignmentId eq request).toList()
    }

    override suspend fun submitAssignment(
        assignmentId: String,
        userId: String,
        attachments: List<AssignmentAttachment>
    ): Boolean {
        val user = users.findOne(User::id eq userId) ?: User()
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
                    userName = user.name,
                    assignmentId = assignmentId,
                    userId = userId,
                    attachments = attachments
                )
            ).wasAcknowledged()
        }
    }

    override suspend fun submitReview(request: AssignmentRequest.SubmitReview): Boolean {
        val userAssignment = userAssignments.findOne(UserAssignmentSubmission::id eq request.submissionId) ?: return false
        return userAssignments.updateOne(
            UserAssignmentSubmission::id eq request.submissionId,
            userAssignment.copy(grade = request.grade, feedback = request.feedback, isReviewed = true, isAccepted = true, isReturned = false)
        ).wasAcknowledged()

    }

    override suspend fun getAssignments(communityId: String): List<Assignment> {

        return assignments.find(AssignmentEntity::communityId eq communityId).toList().map { it.toModel() }
    }
}