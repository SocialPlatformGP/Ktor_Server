package com.example.routes.material

import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import java.io.File

fun Route.accessFiles() {
    staticFiles("/", File("files"))
}