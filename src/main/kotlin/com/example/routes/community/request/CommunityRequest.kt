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
    ) : CommunityRequest()
    @Serializable
    data class AcceptCommunityRequest(
        val requestId: String
    ) : CommunityRequest()
    @Serializable
    data class DeclineCommunityRequest(
        val requestId: String
    ) : CommunityRequest()
    @Serializable
    data class FetchCommunityMembersRequests(
        val communityId: String
    ) : CommunityRequest()
    @Serializable
    data class FetchCommunity(
        val communityId: String
    ) : CommunityRequest()
    @Serializable
    data class DeleteCommunity(val communityId: String)
    @Serializable
    data class EditCommunity(val community: Community)
}