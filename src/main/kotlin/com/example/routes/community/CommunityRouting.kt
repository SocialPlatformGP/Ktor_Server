package com.example.routes.community

import com.example.repository.community.CommunityRepository
import io.ktor.server.routing.*

fun Route.communityRouting(
    communityRepo : CommunityRepository
) {
    getUserCommunities(
        commRepo = communityRepo
    )
    editCommunity(
        commReo = communityRepo
    )
    deleteCommunity(
        communityRepository = communityRepo
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
    acceptCommunityRequest(
        commRepo = communityRepo
    )
    declineCommunityRequest(
        commRepo = communityRepo
    )
    fetchCommunity(
        commRepo = communityRepo
    )
    fetchCommunityMembers(
        commRepo = communityRepo
    )


}