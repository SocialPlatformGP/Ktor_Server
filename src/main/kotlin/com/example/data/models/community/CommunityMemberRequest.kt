package com.example.data.models.community

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class CommunityMemberRequest(
    val id: String = ObjectId().toString(),
    val communityId: String = "",
    val userId: String = "",
    val userName: String = "",
    val userAvatar: String = ""
)
