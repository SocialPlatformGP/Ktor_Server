package com.example.repository.grade

import com.example.data.models.grades.Grades
import com.example.data.requests.GradesRequest
interface GradesRepository {
    suspend fun uploadGradesFile(grades:List<Grades>): Boolean
    suspend fun getGrades(request: GradesRequest.GetGrades): List<Grades>
}