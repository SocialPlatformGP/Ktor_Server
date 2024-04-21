package com.example.repository

import com.example.data.models.user.User
import com.example.data.responses.UserResponse

interface AuthRepository {
    suspend fun createUser(user: User): User?
    suspend fun findUserByEmail(email:String): User?
    suspend fun findUserById(id:String): User?

    suspend fun getAllUsers(): List<User>
   suspend fun getUsersByIds(ids: List<String>): List<UserResponse>
}