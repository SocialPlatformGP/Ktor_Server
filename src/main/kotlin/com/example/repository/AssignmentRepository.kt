package com.example.repository

import com.example.data.models.assignment.Assignment

interface AssignmentRepository {
    suspend fun createAssignment(request: Assignment): String
}