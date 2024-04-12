package com.example

import com.example.plugins.*
import com.example.repository.*
import com.example.room.RoomController
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.token.TokenConfig
import io.ktor.server.application.*
import org.koin.ktor.ext.inject


fun main(args: Array<String>) =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    val hashingService: HashingService by inject()
    val authRepository: AuthRepository by inject()
    val postRepository: PostRepository by inject()
    val replyRepository: ReplyRepository by inject()
    val materialRepository: MaterialRepository by inject()
    val tokenService: TokenService by inject()
    val roomController: RoomController by inject()
    val messageDataSource: MessageDataSource by inject()
    val tokenConfig = TokenConfig(
        issuer = "http://0.0.0.0:8080/",
        audience = "http://0.0.0.0:8080/hello",
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = "secret"
    )

//    configureHTTP()
    configureDependencyInjection2()
    configureSecurity2(tokenConfig)
    configureMonitoring()
    configureSerialization()
    configureSession2()
    configureSockets2()

    configureRouting2(
        hashingService = hashingService,
        authRepository = authRepository,
        postRepository = postRepository,
        tokenService = tokenService,
        tokenConfig = tokenConfig,
        roomController = roomController,
        materialRepository = materialRepository,
        replyRepository = replyRepository,
        messageDataSource = messageDataSource
    )

}
