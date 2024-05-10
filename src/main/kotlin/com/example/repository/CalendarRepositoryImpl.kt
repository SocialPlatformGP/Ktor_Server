package com.example.repository

import com.example.data.models.calendar.CalendarEvent
import com.example.data.models.calendar.CommunityEvents
import com.example.data.models.calendar.UserEvents
import com.example.data.requests.CalendarRequest
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class CalendarRepositoryImpl(db: CoroutineDatabase) : CalendarRepository {
    private val events = db.getCollection<CalendarEvent>()
    private val userEvents = db.getCollection<UserEvents>()
    private val communityEvents = db.getCollection<CommunityEvents>()
    override suspend fun getUserEvents(request: CalendarRequest.GetUserEvents): List<CalendarEvent> {
        return userEvents.findOne(UserEvents::userId eq request.userId)?.events ?: emptyList()
    }

    override suspend fun createUserEvent(request: CalendarRequest.CreateEvent): Boolean {
        val event = CalendarEvent(
            date = request.event.date,
            title = request.event.title,
            type = request.event.type,
            description = request.event.description,
        )
        events.insertOne(event)
        val userEvent = userEvents.findOne(UserEvents::userId eq request.userId)
        if (userEvent != null) {
            userEvents.updateOne(
                UserEvents::userId eq request.userId,
                userEvent.copy(events = userEvent.events + event)
            )
        } else {
            userEvents.insertOne(UserEvents(request.userId, listOf(event)))
        }
        return true
    }
}