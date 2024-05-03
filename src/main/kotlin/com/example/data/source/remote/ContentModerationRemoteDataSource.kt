package com.example.data.source.remote

import com.example.data.models.moderation.ValidateTextResponse

interface ContentModerationRemoteDataSource {
    suspend fun validateText(text: String): ValidateTextResponse?
    suspend fun validateImage(url: String): ValidateTextResponse?
}

