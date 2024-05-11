package com.example.routes.calendar

import com.example.data.requests.CalendarRequest
import com.example.repository.calendar.CalendarRepository
import com.example.utils.CalendarError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getEvents(calendarRepository: CalendarRepository) {
    post("/getUserEvents") {
        val request = call.receiveNullable<CalendarRequest.GetUserEvents>()?: return@post call.respond(HttpStatusCode.BadRequest,CalendarError.SERVER_ERROR)
        val response = calendarRepository.getUserEvents(request)
        call.respond(response)
    }
}