package com.example.repository

import com.example.data.models.grades.Grade
import com.example.data.models.grades.Grades
import com.example.data.models.user.User
import com.example.data.requests.GradesRequest
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.`in`

class GradesRepositoryImpl(
    db: CoroutineDatabase
) : GradesRepository {
    private val gradesDb = db.getCollection<Grades>()
    private val usersDb = db.getCollection<User>()
    override suspend fun uploadGradesFile(grades: List<Grades>): Boolean {
        val users = grades.map { it.userId }
        val oldGrades = gradesDb.find(Grades::userId `in` users).toList()
        grades.forEach { newGrade ->
            val oldGrade = oldGrades.find { it.userId == newGrade.userId }
            if (oldGrade != null) {
                val result = oldGrade.grade.map {old->
                    val new = newGrade.grade.find {it.topic == old.topic}
                        if(new != null){
                            old.copy(grade =new.grade)
                        }
                        else{
                            old
                        }

                }
                gradesDb.updateOne(Grades::id eq oldGrade.id, oldGrade.copy(grade = result))
                    .wasAcknowledged()
            } else {
                gradesDb.insertOne(newGrade).wasAcknowledged()
            }
        }
        return true

    }

    override suspend fun getGrades(request: GradesRequest.GetGrades): List<Grades> {
        println(request.userName)
        val user = usersDb.findOne(User::id eq request.userName) ?: return emptyList()
        return gradesDb.find(Grades::userName eq user.name).toList()
    }
}