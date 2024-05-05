package com.example.routes.assignment

import com.example.repository.AssignmentRepository
import com.example.repository.CommunityRepository
import io.ktor.server.routing.*

fun Routing.assignmentRouting(
    assignmentRepository: AssignmentRepository,
    communityRepository: CommunityRepository
) {
    getAssignment(assignmentRepository)
    getSubmissions(assignmentRepository)
    createAssignment(assignmentRepository)
    submitAssignment(assignmentRepository)
    getAssignments(
        assignmentRepository,
        communityRepository
    )
    getAttachments(assignmentRepository)
}