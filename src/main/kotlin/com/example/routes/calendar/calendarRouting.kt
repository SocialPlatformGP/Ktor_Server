package com.example.routes.calendar

import com.example.repository.CalendarRepository
import io.ktor.server.routing.*

fun Route.calendarRouting(
    calendarRepository: CalendarRepository
) {
    createEvent(calendarRepository)
    getEvents(calendarRepository)
}
