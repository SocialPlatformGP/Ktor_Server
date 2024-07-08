package com.example.data.responses

import com.example.utils.ModerationSafety

@kotlinx.serialization.Serializable
data class ModerationResponse(
    val safety: ModerationSafety,
    val score: Double,
)
