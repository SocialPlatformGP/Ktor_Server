package com.example.routes.community.request

import com.example.data.models.community.Community
import kotlinx.serialization.Serializable

sealed class CommunityRequest {
    @Serializable
    data class CreateCommunity(
        val community: Community,
        val creatorId: String
    ) : CommunityRequest()
    @Serializable
    data class LogoutCommunity(
        val id: String,
        val selectedCommunityId: String
    ) : CommunityRequest()
    @Serializable
    data class JoinCommunity(
        val id: String,
        val code: String
    ): CommunityRequest()
}