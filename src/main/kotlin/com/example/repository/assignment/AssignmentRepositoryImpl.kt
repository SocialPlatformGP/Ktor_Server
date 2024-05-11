package com.example.repository.assignment

import com.example.data.models.assignment.Assignment
import com.example.data.models.assignment.AssignmentAttachment
import com.example.data.models.assignment.AssignmentEntity
import com.example.data.models.assignment.UserAssignmentSubmission
import com.example.data.models.calendar.CalendarEvent
import com.example.data.models.calendar.CommunityEvents
import com.example.data.models.user.User
import com.example.data.requests.AssignmentRequest
import com.example.utils.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class AssignmentRepositoryImpl(db: CoroutineDatabase) : AssignmentRepository {
    private val assignments = db.getCollection<AssignmentEntity>()
    private val userAssignments = db.getCollection<UserAssignmentSubmission>()
    private val users = db.getCollection<User>()
    private val events = db.getCollection<CalendarEvent>()
    private val communityEvents = db.getCollection<CommunityEvents>()


    override suspend fun createAssignment(request: Assignment): Boolean {
        val user = users.findOne(User::id eq request.creatorId)
        val event = CalendarEvent(
            date = request.dueDate,
            title = request.title,
            type = "Assignment",
            description = request.description,
        )
        events.insertOne(event)
        val communityEvent = communityEvents.findOne(CommunityEvents::communityId eq request.communityId)
        if (communityEvent != null) {
            communityEvents.updateOne(
                CommunityEvents::communityId eq request.communityId,
                communityEvent.copy(events = communityEvent.events + event)
            )
        } else {
            communityEvents.insertOne(CommunityEvents(request.communityId, listOf(event)))
        }
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
        return userAssignments.find(UserAssignmentSubmission::assignmentId eq request).toList().filter {
            it.isTurnedIn
        }
    }

    override suspend fun canSubmit(request: String): Boolean {
        val assignment = assignments.findOne(AssignmentEntity::id eq request) ?: return false
        if(assignment.acceptLateSubmissions) return true
        return  LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds < assignment.dueDate
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

    override suspend fun turnInAssignment(request: String): Boolean {
        val userAssignment = userAssignments.findOne(UserAssignmentSubmission::id eq request) ?: return false
        return userAssignments.updateOne(
            UserAssignmentSubmission::id eq request,
            userAssignment.copy(
                isTurnedIn = true,
                submittedAt = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds
            )
        ).wasAcknowledged()
    }

    override suspend fun unSubmitAssignment(request: String): Boolean {
        val userAssignment = userAssignments.findOne(UserAssignmentSubmission::id eq request) ?: return false
        return userAssignments.updateOne(
            UserAssignmentSubmission::id eq request,
            userAssignment.copy(
                isTurnedIn = false,
                submittedAt = 0L
            )
        ).wasAcknowledged()
    }

    override suspend fun getAssignments(communityId: String): List<Assignment> {

        return assignments.find(AssignmentEntity::communityId eq communityId).toList().map { it.toModel() }
    }
}