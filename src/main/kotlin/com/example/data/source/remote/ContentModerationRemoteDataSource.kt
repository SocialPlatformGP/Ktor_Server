package com.example.data.source.remote

import com.example.data.models.moderation.ValidationResponse

interface ContentModerationRemoteDataSource {
    suspend fun validateText(text: String): ValidationResponse?
    suspend fun validateImage(url: String): ValidationResponse?
}

