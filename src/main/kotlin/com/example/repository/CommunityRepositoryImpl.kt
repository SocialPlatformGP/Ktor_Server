package com.example.repository

import com.example.data.models.community.Community
import com.example.data.models.community.CommunityEntity
import com.example.data.models.community.UserCommunities
import com.example.data.models.user.User
import com.example.routes.community.request.CommunityRequest
import com.example.utils.DataError
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setTo

class CommunityRepositoryImpl(db: CoroutineDatabase) : CommunityRepository {
    val communities = db.getCollection<CommunityEntity>()
    val userCommunities = db.getCollection<UserCommunities>()
    val users = db.getCollection<User>()
    override suspend fun getUserCommunities(userId: String): List<Community> {
        val result = userCommunities.findOne(UserCommunities::id eq userId)?.groups?.mapNotNull { commId ->
            communities.findOne(CommunityEntity::id eq commId)?.toCommunity()
        }
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
                comm.allowedEmailDomains.any { domain -> email.endsWith(domain) }
            }
        }

    }

    override suspend fun joinCommunity(request: CommunityRequest.JoinCommunity): List<Community> {
        val comm = communities.findOne(CommunityEntity::code eq request.code) ?: return emptyList()
        val userComm = userCommunities.findOne(UserCommunities::id eq request.id)
        if (userComm == null) {
            userCommunities.insertOne(UserCommunities(request.id, mutableListOf(comm.id)))
        } else {
            userCommunities.updateOne(
                UserCommunities::id eq request.id,
                userComm.copy(groups = userComm.groups + comm.id)
            )

        }
        return userComm?.groups?.mapNotNull { commId ->
            communities.findOne(CommunityEntity::id eq commId)?.toCommunity()
        } ?: emptyList()

    }

    override suspend fun createCommunity(request: CommunityRequest.CreateCommunity): Community {
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
        return comm.toCommunity()
    }
}