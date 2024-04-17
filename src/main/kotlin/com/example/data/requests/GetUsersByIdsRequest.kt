package com.example.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class GetUsersByIdsRequest(
    val ids: List<String> = emptyList(),
)
