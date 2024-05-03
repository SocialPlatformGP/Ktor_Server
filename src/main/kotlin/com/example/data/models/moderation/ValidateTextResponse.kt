package com.example.data.models.moderation

import kotlinx.serialization.Serializable

@Serializable
data class ValidateTextResponse(
    val result: List<ValidateTextResult>,
)