package com.example.data.requests

import kotlinx.serialization.Serializable
@Serializable
sealed class UpdateUserRequest {
    @Serializable
    data class UpdatePhoneNumber(val userId: String, val phoneNumber: String) : UpdateUserRequest()
    @Serializable
    data class UpdateName(val userId: String, val name: String) : UpdateUserRequest()
    @Serializable
    data class UpdateTheme(val userId: String, val theme: String) : UpdateUserRequest()
    @Serializable
    data class UpdateAllowMessagesFrom(val userId: String, val allowMessagesFrom: String) : UpdateUserRequest()
    @Serializable
    data class UpdateWhoCanAddToGroups(val userId: String, val whoCanAddToGroups: String) : UpdateUserRequest()
    @Serializable
    data class UpdateIsNotificationsAllowed(val userId: String, val isNotificationsAllowed: Boolean) : UpdateUserRequest()
    @Serializable
    data class UpdateIsPostNotificationsAllowed(val userId: String, val isPostNotificationsAllowed: Boolean) : UpdateUserRequest()
    @Serializable
    data class UpdateIsChatNotificationsAllowed(val userId: String, val isChatNotificationsAllowed: Boolean) : UpdateUserRequest()
    @Serializable
    data class UpdateIsAssignmentsNotificationsAllowed(val userId: String, val isAssignmentsNotificationsAllowed: Boolean) : UpdateUserRequest()
    @Serializable
    data class UpdateIsCalendarNotificationsAllowed(val userId: String, val isCalendarNotificationsAllowed: Boolean) : UpdateUserRequest()
    @Serializable
    data class UpdateEmail(val userId: String, val email: String) : UpdateUserRequest()
    @Serializable
    data class UpdateAvatarUrl(val userId: String, val avatarUrl: String) : UpdateUserRequest()
    @Serializable
    data class UpdateBio(val userId: String, val bio: String) : UpdateUserRequest()
}
