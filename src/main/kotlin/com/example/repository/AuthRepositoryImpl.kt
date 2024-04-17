package com.example.repository

import com.example.data.models.user.User
import com.example.data.responses.UserResponse

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.`in`
import java.io.InputStream

class AuthRepositoryImpl(db: CoroutineDatabase) : AuthRepository {
    private val users = db.getCollection<User>()
    override suspend fun createUser(user: User): User? {
        val success = users.insertOne(user).wasAcknowledged()
        if (!success) throw Exception("Could not create user")
        else return users.findOne(User::email eq user.email)
    }
    override suspend fun findUserByEmail(email: String): User? =
        users.findOne(User::email eq email)

    override suspend fun findUserById(id: String): User? =
        users.findOne(User::id eq id)

    override suspend fun getAllUsers(): List<User> {
        val result = users.find().toList()
        println("Result: $result")
        return result
    }

    override suspend fun getUsersByIds(ids: List<String>): List<UserResponse> =
        users.find(User::id `in` ids).toList().map { it.toResponse() }

}
data class File(val name:String,val stream:InputStream)