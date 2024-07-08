package com.example.data.source.remote

import com.example.data.models.moderation.ValidationResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*

class ContentModerationRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : ContentModerationRemoteDataSource {
    override suspend fun validateText(text: String): ValidationResponse? {
        return try{
            val request = httpClient.get{
                endPoint("validate-text")
                parameter("text", text)
                timeout {
                    requestTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
                    socketTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
                }
            }
            if(request.status == HttpStatusCode.OK) {
                val response = request.body<ValidationResponse>()
                response
            } else {
                println("Server Error: ${request.body<String>()},,,${request.status}")
                null
            }
        } catch (e: SocketTimeoutException){
            validateText(text)
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
    override suspend fun validateImage(url: String): ValidationResponse? {
        return try{
            val request = httpClient.get{
                endPoint("validate-image")
                parameter("url", url)
                timeout {
                    requestTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
                    socketTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
                }
            }
            if(request.status == HttpStatusCode.OK) {
                val response = request.body<ValidationResponse>()
                response
            } else {
                println("Server Error: ${request.body<String>()},,,${request.status}")
                null
            }
        } catch (e: SocketTimeoutException){
            validateImage(url)
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}

private fun HttpRequestBuilder.endPoint(path: String) {
    url {
        takeFrom("http://127.0.0.1:8000/")
        path(path)
        contentType(ContentType.Application.Json)
    }
}
