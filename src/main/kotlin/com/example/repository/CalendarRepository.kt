package com.example.repository

import com.example.data.models.calendar.CalendarEvent
import com.example.data.models.calendar.UserEvents
import com.example.data.requests.CalendarRequest
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {
    suspend fun getUserEvents(request: CalendarRequest.GetUserEvents): List<CalendarEvent>
    suspend fun createUserEvent(request: CalendarRequest.CreateEvent): Boolean
}