package com.example.data.requests

import com.example.data.models.calendar.CalendarEvent
import kotlinx.serialization.Serializable

sealed interface CalendarRequest {
    @Serializable
    data class GetUserEvents(
        val userId: String
    ) : CalendarRequest
    @Serializable
    data class CreateEvent(
        val userId: String,
        val event: CalendarEvent
    )
}