package com.example.routes.assignment

import com.example.repository.assignment.AssignmentRepository
import com.example.repository.community.CommunityRepository
import io.ktor.server.routing.*

fun Routing.assignmentRouting(
    assignmentRepository: AssignmentRepository,
    communityRepository: CommunityRepository
) {
    turnInAssignment(assignmentRepository)
    getAssignment(assignmentRepository)
    getSubmissions(assignmentRepository)
    createAssignment(assignmentRepository)
    submitAssignment(assignmentRepository)
    submitReview(assignmentRepository)
    getAssignments(
        assignmentRepository,
        communityRepository
    )
    getAttachments(assignmentRepository)
    unSubmitAssignment(assignmentRepository)
    generateCsv(assignmentRepository)
}