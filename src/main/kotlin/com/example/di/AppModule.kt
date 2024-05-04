package com.example.di

import com.example.data.source.remote.ContentModerationRemoteDataSource
import com.example.data.source.remote.ContentModerationRemoteDataSourceImpl
import com.example.repository.*
import com.example.room.RoomController
import com.example.security.JwtService
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.hashing.SHA256HashingService
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.json
import org.litote.kmongo.reactivestreams.KMongo

val appModule = module {
    single<AuthRepository>{ AuthRepositoryImpl(get()) }
    single<MaterialRepository>{ MaterialRepositoryImpl(get()) }
    single<PostRepository>{ PostRepositoryImpl(get(), get()) }
    single<ReplyRepository>{ ReplyRepositoryImpl(get(), get()) }
    single<CommunityRepository>{ CommunityRepositoryImpl(get()) }
    single<AssignmentRepository>{ AssignmentRepositoryImpl(get()) }
    single<HashingService>{ SHA256HashingService() }
    single<HttpClient>{
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    isLenient = true
                    encodeDefaults = true
                })
            }
        }
    }
    single<ContentModerationRemoteDataSource>{ ContentModerationRemoteDataSourceImpl(get()) }
    single<MessageDataSource>{ MessageDataSourceImpl(get(), get()) }
    single<TokenService>{ JwtService() }
    single {
        RoomController(get(), get())
    }

    single {
        KMongo.createClient().coroutine.getDatabase("EduLink_db")
    }
}