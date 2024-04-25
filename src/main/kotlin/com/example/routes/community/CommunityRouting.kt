package com.example.routes.community

import com.example.data.models.community.Community
import com.example.repository.CommunityRepository
import io.ktor.server.routing.*

fun Route.communityRouting(
    communityRepo : CommunityRepository
) {
    getUserCommunities(
        commRepo = communityRepo
    )
    createCommunity(
        commRepo = communityRepo
    )
    communityLogout(
        commRepo = communityRepo
    )
    joinCommunity(
        commRepo = communityRepo
    )

}