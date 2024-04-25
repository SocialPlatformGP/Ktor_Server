package com.example.repository

import com.example.data.models.user.User

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.`in`
import java.io.InputStream

class AuthRepositoryImpl(db: CoroutineDatabase) : AuthRepository {
    private val users = db.getCollection<User>()
    override suspend fun createUser(user: User): Boolean
         = users.insertOne(user).wasAcknowledged()
    override suspend fun findUserByEmail(email: String): User? =
        users.findOne(User::email eq email)

    override suspend fun findUserById(id: String): User? =
        users.findOne(User::id eq id)

    override suspend fun getAllUsers(): List<User> {
        val result = users.find().toList()
        return result
    }

    override suspend fun getUsersByIds(ids: List<String>): List<User> =
        users.find(User::id `in` ids).toList()

}
data class File(val name:String,val stream:InputStream)