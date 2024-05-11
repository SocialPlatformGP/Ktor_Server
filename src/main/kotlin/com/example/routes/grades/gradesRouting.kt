package com.example.routes.grades

import com.example.repository.grade.GradesRepository
import io.ktor.server.routing.*

fun Route.gradesRouting(
    gradesRepository: GradesRepository
) {
    uploadGradesFile(gradesRepository)
    getGrades(gradesRepository)
}

