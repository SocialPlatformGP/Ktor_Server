package com.example.routes.assignment

import com.example.repository.AssignmentRepository
import io.ktor.server.routing.*

fun Routing.assignmentRouting(
    assignmentRepository: AssignmentRepository
) {
    createAssignment(assignmentRepository)
    submitAssignment(assignmentRepository)
    getAssignments(assignmentRepository)
    getAttachments(assignmentRepository)
}