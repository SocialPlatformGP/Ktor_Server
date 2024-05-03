package com.example.data.source.remote

import com.example.data.models.moderation.ValidateTextResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.litote.kmongo.json
import kotlin.text.get

class ContentModerationRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : ContentModerationRemoteDataSource {
    override suspend fun validateText(text: String): ValidateTextResponse? {
        return try{
            val request = httpClient.get{
                endPoint("validate-text")
                parameter("text", text)
            }
            if(request.status == HttpStatusCode.OK) {
                val response = request.body<ValidateTextResponse>()
                response
            } else {
                println("Server Error: ${request.body<String>()},,,${request.status}")
                null
            }
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
    override suspend fun validateImage(url: String): ValidateTextResponse? {
        return try{
            val request = httpClient.get{
                endPoint("validate-text")
                parameter("url", url)
                timeout {
                    requestTimeoutMillis = 0
                }
            }
            if(request.status == HttpStatusCode.OK) {
                val response = request.body<ValidateTextResponse>()
                response
            } else {
                println("Server Error: ${request.body<String>()},,,${request.status}")
                null
            }
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}

private fun HttpRequestBuilder.endPoint(path: String) {
    url {
        takeFrom("http://192.168.1.7:8000/")
        path(path)
        contentType(ContentType.Application.Json)
    }
}
