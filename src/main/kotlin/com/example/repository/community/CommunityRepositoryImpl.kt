package com.example.repository.community

import com.example.data.models.community.Community
import com.example.data.models.community.CommunityEntity
import com.example.data.models.community.CommunityMemberRequest
import com.example.data.models.community.UserCommunities
import com.example.data.models.user.User
import com.example.routes.community.request.CommunityRequest
import org.litote.kmongo.contains
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class CommunityRepositoryImpl(db: CoroutineDatabase) : CommunityRepository {
    val communities = db.getCollection<CommunityEntity>()
    val userCommunities = db.getCollection<UserCommunities>()
    val communityRequests = db.getCollection<CommunityMemberRequest>()
    val users = db.getCollection<User>()
    override suspend fun checkCode(request: CommunityRequest.JoinCommunity): Boolean {
        val comm = communities.findOne(CommunityEntity::code eq request.code) ?: return false
        return comm.code == request.code
    }

    override suspend fun isrequireApproval(request: CommunityRequest.JoinCommunity): Boolean {
        val comm = communities.findOne(CommunityEntity::code eq request.code) ?: return false
        return comm.isAdminApprovalRequired
    }

    override suspend fun getUserCommunities(userId: String): List<Community> {
        println("\n\n\n\n" + userId + "\n\n\n\n")
        val result = userCommunities.findOne(UserCommunities::id eq userId)?.groups?.mapNotNull { commId ->
            communities.findOne(CommunityEntity::id eq commId)?.toCommunity()
        }
        println("\n\n\n\n" + result + "\n\n\n\n")
        return result ?: emptyList()
    }

    override suspend fun logoutCommunity(request: CommunityRequest.LogoutCommunity): List<Community> {
        println("\n\n\n\n" + request.id + request.selectedCommunityId + "\n\n\n\n")
        val userComm = userCommunities.findOne(UserCommunities::id eq request.id) ?: return emptyList()
        val editedComm = userComm.groups.filter { it != request.selectedCommunityId }
        userCommunities.updateOne(UserCommunities::id eq request.id, userComm.copy(groups = editedComm))
        return editedComm.mapNotNull { commId ->
            communities.findOne(CommunityEntity::id eq commId)?.toCommunity()
        }
    }

    override suspend fun checkCommunityDomain(request: CommunityRequest.JoinCommunity): Boolean {
        val comm = communities.findOne(CommunityEntity::code eq request.code) ?: return false
        val user = users.findOne(User::id eq request.id) ?: return false
        return if (comm.allowAnyEmailDomain) {
            true
        } else {
            user.email.let { email ->
                comm.allowedEmailDomains.any { domain -> email.split("@")[1] == domain }
            }
        }
    }

    override suspend fun joinCommunity(request: CommunityRequest.JoinCommunity): Boolean   {
        val comm = communities.findOne(CommunityEntity::code eq request.code)?: return false
        val userComm = userCommunities.findOne(UserCommunities::id eq request.id)
        if(comm.isAdminApprovalRequired){
            val user = users.findOne(User::id eq request.id) ?: return false
            communityRequests.insertOne(CommunityMemberRequest(
                communityId = comm.id,
                userId = user.id,
                userName = user.name,
                userAvatar = user.profilePictureURL
            ))
            return true
        }
        if (userComm == null) {
            userCommunities.insertOne(UserCommunities(request.id, mutableListOf(comm.id)))
            return true
        } else {
            userCommunities.updateOne(
                UserCommunities::id eq request.id,
                userComm.copy(groups = userComm.groups + comm.id)
            )
            return true
        }
    }

    override suspend fun isAlreadyMember(request: CommunityRequest.JoinCommunity): Boolean {
        val comm = communities.findOne(CommunityEntity::code eq request.code) ?: return false
        val userComm = userCommunities.findOne(UserCommunities::id eq request.id) ?: return false
        return userComm.groups.contains(comm.id)
    }

    override suspend fun acceptCommunityRequest(request: CommunityRequest.AcceptCommunityRequest): Boolean {
        val userRequest = communityRequests.findOne(CommunityMemberRequest::id eq request.requestId) ?: return false
        val userComm = userCommunities.findOne(UserCommunities::id eq userRequest.userId)
        if (userComm == null) {
            userCommunities.insertOne(UserCommunities(userRequest.userId, mutableListOf(userRequest.communityId)))
        } else {
            userCommunities.updateOne(
                UserCommunities::id eq userRequest.userId,
                userComm.copy(groups = userComm.groups + userRequest.communityId)
            )
        }
        val comm = communities.findOne(CommunityEntity::id eq userRequest.communityId) ?: return false
        communities.updateOne(CommunityEntity::id eq userRequest.communityId, comm.copy(members = comm.members.plus( userRequest.userId to false)))
        communityRequests.deleteOne(CommunityMemberRequest::id eq request.requestId)
        return true
    }

    override suspend fun deleteCommunity(request: CommunityRequest.DeleteCommunity): Boolean {
        communities.deleteOne(CommunityEntity::id eq request.communityId)
        val usersComm = userCommunities.find(UserCommunities::groups contains  request.communityId).toList()
        usersComm.forEach {user->
            userCommunities.updateOne(UserCommunities::id eq user.id, user.copy(groups = user.groups.filter { it != request.communityId }))
        }
        communityRequests.deleteMany(CommunityMemberRequest::communityId eq request.communityId)
        return true
    }

    override suspend fun editCommunity(request: CommunityRequest.EditCommunity): Boolean {
        val comm = communities.findOne(CommunityEntity::id eq request.community.id) ?: return false
        val editedComm = comm.copy(
            name = request.community.name,
            description = request.community.description,
            allowAnyEmailDomain = request.community.allowAnyEmailDomain,
            allowedEmailDomains = request.community.allowedEmailDomains,
            isAdminApprovalRequired = request.community.isAdminApprovalRequired
        )
        communities.updateOne(CommunityEntity::id eq request.community.id, editedComm)
        return true
    }

    override suspend fun declineCommunityRequest(request: CommunityRequest.DeclineCommunityRequest): Boolean {
        communityRequests.deleteOne(CommunityMemberRequest::id eq request.requestId)
        return true
    }

    override suspend fun fetchCommunity(request: CommunityRequest.FetchCommunity): Community? {
        val comm = communities.findOne(CommunityEntity::id eq request.communityId) ?: return null
        return comm.toCommunity()
    }

    override suspend fun fetchCommunityMembers(request: CommunityRequest.FetchCommunityMembersRequests): List<CommunityMemberRequest> {
        println("\n\n\n\n" + request.communityId + "\n\n\n\n")
        val comm = communityRequests.find(CommunityMemberRequest::communityId eq request.communityId).toList()
        println("\n\n\n\n" + comm + "\n\n\n\n")
        return comm

    }

    override suspend fun isRequestExist(request: CommunityRequest.JoinCommunity): Boolean {
        val comm = communities.findOne(CommunityEntity::code eq request.code) ?: return false
        val commRequests = communityRequests.find(CommunityMemberRequest::communityId eq comm.id).toList()
        return commRequests.any { it.userId == request.id }
    }


    override suspend fun createCommunity(request: CommunityRequest.CreateCommunity): Community {
        println("\n\n\n\n" + request.community + request.creatorId + "\n\n\n\n")
        val comm = request.community.toEntity(request.creatorId)
        communities.insertOne(comm)
        val userComm = userCommunities.findOne(UserCommunities::id eq request.creatorId)
        if (userComm == null) {
            userCommunities.insertOne(UserCommunities(request.creatorId, mutableListOf(comm.id)))
        } else {
            userCommunities.updateOne(
                UserCommunities::id eq request.creatorId,
                userComm.copy(groups = userComm.groups + comm.id)
            )
        }
        println("\n\n\n\n" + comm.toCommunity() + "\n\n\n\n")
        return comm.toCommunity()
    }
}