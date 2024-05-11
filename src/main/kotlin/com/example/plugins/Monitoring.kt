package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import org.slf4j.event.Level

fun Application.configureMonitoring2() {
    install(CallLogging) {
        level = Level.INFO
        println(message = this.logger)
        filter { call -> call.request.path().startsWith("/") }
    }
}
