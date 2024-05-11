package com.example.data.models.grades

import org.bson.types.ObjectId

@kotlinx.serialization.Serializable
data class Grades(
    val id : String = ObjectId().toString(),
    val userId: String = "",
    val userName: String = "",
    val communityId : String = "",
    val course: String = "",
    val grade :List<Grade> = emptyList(),
    val totalGrade : Int = grade.sumOf { it.grade }
)

@kotlinx.serialization.Serializable
data class Grade(
    val topic : String = "",
    val grade : Int = 0,
)