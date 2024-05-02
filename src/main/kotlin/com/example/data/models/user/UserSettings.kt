package com.example.data.models.user

import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val userId: String = "",
    val theme: String = "System Default",
    val allowMessagesFrom: String = PrivacyOptions.EVERYONE.value,
    val whoCanAddToGroups: String = PrivacyOptions.EVERYONE.value,
    val isNotificationsAllowed: Boolean = true,
    val isPostNotificationsAllowed: Boolean = true,
    val isChatNotificationsAllowed: Boolean = true,
    val isAssignmentsNotificationsAllowed: Boolean = true,
    val isCalendarNotificationsAllowed: Boolean = true,
)
@Serializable
enum class PrivacyOptions(val value: String) {
    EVERYONE("Everyone"),
    NO_ONE("No one"),
}