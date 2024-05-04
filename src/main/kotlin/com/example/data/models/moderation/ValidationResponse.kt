package com.example.data.models.moderation

import kotlinx.serialization.Serializable

@Serializable
data class ValidationResponse(
    val result: List<ValidationResult>,
)