package com.example.routes.assignment

import com.example.repository.AssignmentRepository
import com.example.utils.CsvUtils
import com.example.utils.CsvUtils.convertCsvToPdf
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.delay

fun Route.generateCsv(
    assignmentRepository: AssignmentRepository
) {
    get("home") {
        val assignments = assignmentRepository.getAssignments("662d8a1820564f41d0a665c5")
            .filter { it.creatorId == "5e60a25e-19d0-4971-81db-f92429f468e9" }
        val submissions = assignments.map {
            assignmentRepository.getSubmissions(it.id)
        }.flatten()
        CsvUtils.generateCsv(assignments, submissions)
        delay(1000)
        convertCsvToPdf("outut.csv", "output.pdf")
        val file = java.io.File("output.pdf")
        call.respondFile(file)

    }
}