package com.example.data.models.community

typealias CommunityId = String

@kotlinx.serialization.Serializable
data class UserCommunities(
    val id: String = "",
    val groups: List<CommunityId> = emptyList()
)

