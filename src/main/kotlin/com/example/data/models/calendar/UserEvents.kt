package com.example.data.models.calendar

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
@Serializable
data class UserEvents(
    val userId: String,
    val events : List<CalendarEvent>
)

@Serializable
data class CalendarEvent(
    val id: String = ObjectId().toString(),
    val date: Long = 0L,
    val title: String = "",
    val time : Long = 0L,
    val type: String = "",
    val description: String = "",
    val location : String= "",
    val user: String = "",
    val communityId: String = "",
    val isPrivate: Boolean = true,
)
@Serializable
data class CommunityEvents(
    val communityId: String,
    val events : List<CalendarEvent>
)
