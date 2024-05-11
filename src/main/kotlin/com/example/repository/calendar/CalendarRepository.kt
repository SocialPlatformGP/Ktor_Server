package com.example.repository.calendar

import com.example.data.models.calendar.CalendarEvent
import com.example.data.requests.CalendarRequest

interface CalendarRepository {
    suspend fun getUserEvents(request: CalendarRequest.GetUserEvents): List<CalendarEvent>
    suspend fun createUserEvent(request: CalendarRequest.CreateEvent): Boolean
}