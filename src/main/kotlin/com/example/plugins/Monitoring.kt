package com.example.plugins

import io.ktor.server.plugins.callloging.*
import org.slf4j.event.*
import io.ktor.server.request.*
import io.ktor.server.application.*

fun Application.configureMonitoring() {
    install(CallLogging) {
        level = Level.INFO
        println(message = this.logger)
        filter { call -> call.request.path().startsWith("/") }
    }
}
