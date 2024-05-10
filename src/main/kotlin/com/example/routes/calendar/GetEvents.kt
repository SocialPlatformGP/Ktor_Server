package com.example.routes.calendar

import com.example.data.requests.CalendarRequest
import com.example.repository.CalendarRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getEvents(calendarRepository: CalendarRepository) {
    post("/getUserEvents") {
        val request = call.receive<CalendarRequest.GetUserEvents>()
        val response = calendarRepository.getUserEvents(request)
        call.respond(response)
    }
}