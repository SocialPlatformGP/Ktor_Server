package com.example.plugins

import com.example.di.appModule2
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureDependencyInjection2() {
    install(Koin) {
        slf4jLogger()
        modules(appModule2)
    }
}