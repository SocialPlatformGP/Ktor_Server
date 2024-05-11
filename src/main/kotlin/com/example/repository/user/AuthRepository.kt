package com.example.repository.user

import com.example.data.models.user.User
import com.example.data.requests.UpdateUserRequest

interface AuthRepository {
    suspend fun createUser(user: User): Boolean
    suspend fun findUserByEmail(email:String): User?
    suspend fun findUserById(id:String): User?

    suspend fun getAllUsers(): List<User>
   suspend fun getUsersByIds(ids: List<String>): List<User>
   suspend fun updatePhoneNumber(request: UpdateUserRequest.UpdatePhoneNumber): Boolean
    suspend fun updateName(request: UpdateUserRequest.UpdateName): Boolean
    suspend fun updateTheme(request: UpdateUserRequest.UpdateTheme): Boolean
    suspend fun updateAllowMessagesFrom(request: UpdateUserRequest.UpdateAllowMessagesFrom): Boolean
    suspend fun updateWhoCanAddToGroups(request: UpdateUserRequest.UpdateWhoCanAddToGroups): Boolean
    suspend fun updateIsNotificationsAllowed(request: UpdateUserRequest.UpdateIsNotificationsAllowed): Boolean
    suspend fun updateIsPostNotificationsAllowed(request: UpdateUserRequest.UpdateIsPostNotificationsAllowed): Boolean
    suspend fun updateIsChatNotificationsAllowed(request: UpdateUserRequest.UpdateIsChatNotificationsAllowed): Boolean
    suspend fun updateIsAssignmentsNotificationsAllowed(request: UpdateUserRequest.UpdateIsAssignmentsNotificationsAllowed): Boolean
    suspend fun updateIsCalendarNotificationsAllowed(request: UpdateUserRequest.UpdateIsCalendarNotificationsAllowed): Boolean
    suspend fun updateEmail(request: UpdateUserRequest.UpdateEmail): Boolean
    suspend fun updateAvatarUrl(request: UpdateUserRequest.UpdateAvatarUrl): Boolean
    suspend fun updateBio(request: UpdateUserRequest.UpdateBio): Boolean

}