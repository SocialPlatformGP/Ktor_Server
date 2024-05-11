package com.example.routes.calendar

import com.example.data.requests.CalendarRequest
import com.example.repository.calendar.CalendarRepository
import com.example.utils.CalendarError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createEvent(calendarRepository: CalendarRepository) {
    post("/createUserEvent") {
        val request = call.receiveNullable<CalendarRequest.CreateEvent>()?: return@post call.respond(HttpStatusCode.BadRequest,CalendarError.SERVER_ERROR)
        val response = calendarRepository.createUserEvent(request)
        if (response) {
            call.respond(HttpStatusCode.OK,)
        } else {
            call.respond(HttpStatusCode.NotFound,CalendarError.SERVER_ERROR)
        }
    }
}