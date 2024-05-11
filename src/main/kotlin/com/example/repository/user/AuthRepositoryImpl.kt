package com.example.repository.user

import com.example.data.models.user.User
import com.example.data.models.user.UserSettings
import com.example.data.requests.UpdateUserRequest

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.`in`
import java.io.InputStream

class AuthRepositoryImpl(db: CoroutineDatabase) : AuthRepository {
    private val users = db.getCollection<User>()
    private val userSetting = db.getCollection<UserSettings>()
    override suspend fun createUser(user: User): Boolean {
        userSetting.insertOne(UserSettings(userId = user.id))
        return users.insertOne(user).wasAcknowledged()
    }

    override suspend fun updatePhoneNumber(request: UpdateUserRequest.UpdatePhoneNumber): Boolean {
        val user = users.findOne(User::id eq request.userId) ?: return false
        val updatedUser = user.copy(phoneNumber = request.phoneNumber)
        return users.updateOne(request.userId, updatedUser).wasAcknowledged()
    }

    override suspend fun updateName(request: UpdateUserRequest.UpdateName): Boolean {
        val user = users.findOne(User::id eq request.userId) ?: return false
        val updatedUser = user.copy(name = request.name)
        return users.updateOne(request.userId, updatedUser).wasAcknowledged()
    }

    override suspend fun updateTheme(request: UpdateUserRequest.UpdateTheme): Boolean {
        val user = userSetting.findOne(UserSettings::userId eq request.userId) ?: return false
        val updatedUser = user.copy(theme = request.theme)
        return users.updateOne(request.userId, updatedUser).wasAcknowledged()
    }

    override suspend fun updateAllowMessagesFrom(request: UpdateUserRequest.UpdateAllowMessagesFrom): Boolean {
        val user = userSetting.findOne(UserSettings::userId eq request.userId) ?: return false
        val updatedUser = user.copy(allowMessagesFrom = request.allowMessagesFrom)
        return users.updateOne(request.userId, updatedUser).wasAcknowledged()
    }

    override suspend fun updateWhoCanAddToGroups(request: UpdateUserRequest.UpdateWhoCanAddToGroups): Boolean {
        val user = userSetting.findOne(UserSettings::userId eq request.userId) ?: return false
        val updatedUser = user.copy(whoCanAddToGroups = request.whoCanAddToGroups)
        return users.updateOne(request.userId, updatedUser).wasAcknowledged()
    }

    override suspend fun updateIsNotificationsAllowed(request: UpdateUserRequest.UpdateIsNotificationsAllowed): Boolean {
        val user = userSetting.findOne(UserSettings::userId eq request.userId) ?: return false
        val updatedUser = user.copy(isNotificationsAllowed = request.isNotificationsAllowed)
        return users.updateOne(request.userId, updatedUser).wasAcknowledged()
    }

    override suspend fun updateIsPostNotificationsAllowed(request: UpdateUserRequest.UpdateIsPostNotificationsAllowed): Boolean {
        val user = userSetting.findOne(UserSettings::userId eq request.userId) ?: return false
        val updatedUser = user.copy(isPostNotificationsAllowed = request.isPostNotificationsAllowed)
        return users.updateOne(request.userId, updatedUser).wasAcknowledged()
    }

    override suspend fun updateIsChatNotificationsAllowed(request: UpdateUserRequest.UpdateIsChatNotificationsAllowed): Boolean {
        val user = userSetting.findOne(UserSettings::userId eq request.userId) ?: return false
        val updatedUser = user.copy(isChatNotificationsAllowed = request.isChatNotificationsAllowed)
        return users.updateOne(request.userId, updatedUser).wasAcknowledged()
    }

    override suspend fun updateIsAssignmentsNotificationsAllowed(request: UpdateUserRequest.UpdateIsAssignmentsNotificationsAllowed): Boolean {
        val user = userSetting.findOne(UserSettings::userId eq request.userId) ?: return false
        val updatedUser = user.copy(isAssignmentsNotificationsAllowed = request.isAssignmentsNotificationsAllowed)
        return users.updateOne(request.userId, updatedUser).wasAcknowledged()
    }

    override suspend fun updateIsCalendarNotificationsAllowed(request: UpdateUserRequest.UpdateIsCalendarNotificationsAllowed): Boolean {
        val user = userSetting.findOne(UserSettings::userId eq request.userId) ?: return false
        val updatedUser = user.copy(isCalendarNotificationsAllowed = request.isCalendarNotificationsAllowed)
        return users.updateOne(request.userId, updatedUser).wasAcknowledged()
    }

    override suspend fun updateEmail(request: UpdateUserRequest.UpdateEmail): Boolean {
        val user = users.findOne(User::id eq request.userId) ?: return false
        val updatedUser = user.copy(email = request.email)
        return users.updateOne(request.userId, updatedUser).wasAcknowledged()
    }

    override suspend fun updateAvatarUrl(request: UpdateUserRequest.UpdateAvatarUrl): Boolean {
        val user = users.findOne(User::id eq request.userId) ?: return false
        val updatedUser = user.copy(profilePictureURL = request.avatarUrl)
        return users.updateOne(request.userId, updatedUser).wasAcknowledged()
    }

    override suspend fun updateBio(request: UpdateUserRequest.UpdateBio): Boolean {
        val user = users.findOne(User::id eq request.userId) ?: return false
        val updatedUser = user.copy(bio = request.bio)
        return users.updateOne(request.userId, updatedUser).wasAcknowledged()
    }


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