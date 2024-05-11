package com.example

import com.example.plugins.*
import com.example.repository.assignment.AssignmentRepository
import com.example.repository.calendar.CalendarRepository
import com.example.repository.community.CommunityRepository
import com.example.repository.grade.GradesRepository
import com.example.repository.material.MaterialRepository
import com.example.repository.post.PostRepository
import com.example.repository.reply.ReplyRepository
import com.example.repository.user.AuthRepository
import com.example.security.token.TokenConfig
import io.ktor.server.application.*
import org.koin.ktor.ext.inject


fun main(args: Array<String>) =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    val authRepository: AuthRepository by inject()
    val postRepository: PostRepository by inject()
    val replyRepository: ReplyRepository by inject()
    val materialRepository: MaterialRepository by inject()
    val communityRepository: CommunityRepository by inject()
    val assignmentRepository: AssignmentRepository by inject()
    val calendarRepository: CalendarRepository by inject()
    val gradesRepository: GradesRepository by inject()
    val tokenConfig = TokenConfig(
        issuer = "http://0.0.0.0:8085/",
        audience = "http://0.0.0.0:8085/hello",
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = "secret"
    )

//    configureHTTP()
    configureDependencyInjection()
    configureSecurity(tokenConfig)
    configureMonitoring2()
    configureSerialization2()
    configureSession()
    configureSockets()
    configureRouting(
        authRepository = authRepository,
        postRepository = postRepository,
        materialRepository = materialRepository,
        replyRepository = replyRepository,
        communityRepository = communityRepository,
        assignmentRepository = assignmentRepository,
        calendarRepository = calendarRepository,
        gradesRepository =  gradesRepository
    )
}

