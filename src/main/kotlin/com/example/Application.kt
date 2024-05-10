package com.example

import com.example.plugins.*
import com.example.repository.*
import com.example.room.RoomController
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.token.TokenConfig
import com.example.utils.CsvUtils
import com.example.utils.CsvUtils.generateCsv
import io.ktor.server.application.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.time.Year


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
    val communityRepository: CommunityRepository by inject()
    val assignmentRepository: AssignmentRepository by inject()
    val calendarRepository: CalendarRepository by inject()
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
        roomController = roomController,
        materialRepository = materialRepository,
        replyRepository = replyRepository,
        messageDataSource = messageDataSource,
        communityRepository = communityRepository,
        assignmentRepository = assignmentRepository,
        calendarRepository = calendarRepository,
    )
}

//data class Movie(
//    val year: Year,
//    val score: Int,
//    val title: String,
//)
//val dummy = listOf(
//    Movie(Year.of(1994), 9, "The Shawshank Redemption"),
//    Movie(Year.of(1972), 9, "The Godfather"),
//    Movie(Year.of(1974), 9, "The Godfather: Part II"),
//    Movie(Year.of(2008), 9, "The Dark Knight"),
//    Movie(Year.of(1957), 9, "")
//)
//fun OutputStream.writeCsv(movies: List<Movie>) {
//    val writer = bufferedWriter()
//    writer.write(""""Year", "Score", "Title"""")
//    writer.newLine()
//    movies.forEach {
//        writer.write("${it.year}, ${it.score}, \"${it.title}\"")
//        writer.newLine()
//    }
//    writer.flush()
//
//}
