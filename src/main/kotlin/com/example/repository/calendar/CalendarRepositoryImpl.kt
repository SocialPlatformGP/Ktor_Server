package com.example.repository.calendar

import com.example.data.models.calendar.CalendarEvent
import com.example.data.models.calendar.UserEvents
import com.example.data.models.community.CommunityMemberRequest
import com.example.data.requests.CalendarRequest
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class CalendarRepositoryImpl(db: CoroutineDatabase) : CalendarRepository {
    private val events = db.getCollection<CalendarEvent>()
    private val userEvents = db.getCollection<UserEvents>()
    private val communityRequests = db.getCollection<CommunityMemberRequest>()

    override suspend fun getUserEvents(request: CalendarRequest.GetUserEvents): List<CalendarEvent> {
        return userEvents.findOne(UserEvents::userId eq request.userId)?.events ?: emptyList()
    }

    override suspend fun createUserEvent(request: CalendarRequest.CreateEvent): Boolean {
        val event = CalendarEvent(
            date = request.event.date,
            title = request.event.title,
            time = request.event.time,
            type = request.event.type,
            location = request.event.location,
            user = request.event.user,
            description = request.event.description,
            communityId = request.event.communityId,
            isPrivate = request.event.isPrivate
        )

        events.insertOne(event)
        if(request.event.isPrivate) {
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
        }else{
            val comm = communityRequests.find(CommunityMemberRequest::communityId eq request.event.communityId).toList()
            comm.forEach {
                val userEvent = userEvents.findOne(UserEvents::userId eq it.userId)
                if (userEvent != null) {
                    userEvents.updateOne(
                        UserEvents::userId eq it.userId,
                        userEvent.copy(events = userEvent.events + event)
                    )
                } else {
                    userEvents.insertOne(UserEvents(it.userId, listOf(event)))
                }
            }
            return true

        }
    }
}