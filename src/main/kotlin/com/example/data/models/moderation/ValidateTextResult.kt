package com.example.data.models.moderation

import kotlinx.serialization.Serializable

@Serializable
data class ValidateTextResult(
    val label: String,
    val score: Double
)