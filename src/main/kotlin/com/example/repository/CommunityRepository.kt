package com.example.repository

import com.example.data.models.community.Community
import com.example.data.models.community.CommunityMemberRequest
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
    suspend fun acceptCommunityRequest(request: CommunityRequest.AcceptCommunityRequest): Boolean
    suspend fun declineCommunityRequest(request: CommunityRequest.DeclineCommunityRequest): Boolean
    suspend fun fetchCommunity(request: CommunityRequest.FetchCommunity): Community?
    suspend fun fetchCommunityMembers(request: CommunityRequest.FetchCommunityMembersRequests): List<CommunityMemberRequest>
    suspend fun checkCode(request: CommunityRequest.JoinCommunity): Boolean
    suspend fun isrequireApproval(request: CommunityRequest.JoinCommunity): Boolean
    suspend fun isRequestExist(request: CommunityRequest.JoinCommunity): Boolean
    suspend fun deleteCommunity(request: CommunityRequest.DeleteCommunity): Boolean
    suspend fun editCommunity(request: CommunityRequest.EditCommunity): Boolean

}
