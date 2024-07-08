package com.example.data.models.moderation

import kotlinx.serialization.Serializable

@Serializable
data class ValidationResult(
    val label: String,
    val score: Double,
)