package com.example.repository

import com.example.data.models.community.Community
import com.example.routes.community.request.CommunityRequest
import com.example.utils.DataError
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun getUserCommunities(userId :String): List<Community>
    suspend fun createCommunity(request : CommunityRequest.CreateCommunity): Community
    suspend fun logoutCommunity(request: CommunityRequest.LogoutCommunity): List<Community>
    suspend fun joinCommunity(request: CommunityRequest.JoinCommunity): Boolean
    suspend fun checkCommunityDomain(request: CommunityRequest.JoinCommunity): Boolean
    suspend fun isAlreadyMember(request: CommunityRequest.JoinCommunity): Boolean

}
