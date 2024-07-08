package com.example.di

import com.example.data.source.remote.ContentModerationRemoteDataSource
import com.example.data.source.remote.ContentModerationRemoteDataSourceImpl
import com.example.repository.assignment.AssignmentRepository
import com.example.repository.assignment.AssignmentRepositoryImpl
import com.example.repository.calendar.CalendarRepository
import com.example.repository.calendar.CalendarRepositoryImpl
import com.example.repository.community.CommunityRepository
import com.example.repository.community.CommunityRepositoryImpl
import com.example.repository.grade.GradesRepository
import com.example.repository.grade.GradesRepositoryImpl
import com.example.repository.material.MaterialRepository
import com.example.repository.material.MaterialRepositoryImpl
import com.example.repository.post.PostRepository
import com.example.repository.post.PostRepositoryImpl
import com.example.repository.reply.ReplyRepository
import com.example.repository.reply.ReplyRepositoryImpl
import com.example.repository.user.AuthRepository
import com.example.repository.user.AuthRepositoryImpl
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val appModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<MaterialRepository> { MaterialRepositoryImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get(), get()) }
    single<ReplyRepository> { ReplyRepositoryImpl(get(), get()) }
    single<CommunityRepository> { CommunityRepositoryImpl(get()) }
    single<AssignmentRepository> { AssignmentRepositoryImpl(get()) }
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    isLenient = true
                    encodeDefaults = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
                socketTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
            }
        }
    }
    single<ContentModerationRemoteDataSource> { ContentModerationRemoteDataSourceImpl(get()) }
    single<CalendarRepository> { CalendarRepositoryImpl(get()) }
    single<GradesRepository> { GradesRepositoryImpl(get()) }

    single {
        KMongo.createClient().coroutine.getDatabase("EduLink_db")
    }
}