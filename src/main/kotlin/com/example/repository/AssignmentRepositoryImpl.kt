package com.example.repository

import com.example.data.models.assignment.Assignment
import com.example.data.models.post.Post
import org.litote.kmongo.coroutine.CoroutineDatabase

class AssignmentRepositoryImpl(db: CoroutineDatabase): AssignmentRepository {
    override suspend fun createAssignment(request: Assignment): String {
        TODO("Not yet implemented")
    }
}