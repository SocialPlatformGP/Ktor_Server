package com.example.data.models.community

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

typealias UserId = String
typealias domain = String
typealias isAdmin = Boolean

@kotlinx.serialization.Serializable
data class Community(
    val id: String = "",
    val code: String = "",
    val name: String = "",
    val description: String = "",
    val members: Map<UserId, isAdmin> = emptyMap(),
    val isAdminApprovalRequired: Boolean = false,
    val allowAnyEmailDomain: Boolean = true,
    val allowedEmailDomains: Set<domain> = emptySet(),
) {
    companion object{
        fun generateJoinCode(): String {
            val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
            return (1..8)
                .map { allowedChars.random() }
                .joinToString("")
        }
    }
    fun toEntity(userId:String): CommunityEntity {
        return CommunityEntity(
            code = generateJoinCode(),
            name = name,
            description = description,
            members = mapOf(userId to true),
            isAdminApprovalRequired = isAdminApprovalRequired,
            allowAnyEmailDomain = allowAnyEmailDomain,
            allowedEmailDomains = allowedEmailDomains
        )
    }
}
@kotlinx.serialization.Serializable
data class CommunityEntity(
    @BsonId
    val id: String = ObjectId().toString(),
    val code: String = "",
    val name: String = "",
    val description: String = "",
    val members: Map<UserId, isAdmin> = emptyMap(),
    val isAdminApprovalRequired: Boolean = false,
    val allowAnyEmailDomain: Boolean = true,
    val allowedEmailDomains: Set<domain> = emptySet(),
){
    fun toCommunity(): Community {
        return Community(
            id = id,
            code = code,
            name = name,
            description = description,
            members = members,
            isAdminApprovalRequired = isAdminApprovalRequired,
            allowAnyEmailDomain = allowAnyEmailDomain,
            allowedEmailDomains = allowedEmailDomains
        )
    }
}



