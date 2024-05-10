package com.example.routes.calendar

import com.example.data.requests.CalendarRequest
import com.example.repository.CalendarRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createEvent(calendarRepository: CalendarRepository) {
    post("/createUserEvent") {
        val request = call.receive<CalendarRequest.CreateEvent>()
        val response = calendarRepository.createUserEvent(request)
        if (response) {
            call.respond(HttpStatusCode.OK,)
        } else {
            call.respond("Failed to create event")
        }
    }
}