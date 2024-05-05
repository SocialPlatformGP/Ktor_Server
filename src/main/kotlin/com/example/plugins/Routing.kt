package com.example.plugins

import com.example.repository.*
import com.example.room.RoomController
import com.example.routes.assignment.assignmentRouting
import com.example.routes.auth.*
import com.example.routes.chat.*
import com.example.routes.community.communityRouting
import com.example.routes.material.*
import com.example.routes.post.*
import com.example.routes.reply.replyRouting
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    authRepository: AuthRepository,
    postRepository: PostRepository,
    roomController: RoomController,
    materialRepository: MaterialRepository,
    replyRepository: ReplyRepository,
    messageDataSource: MessageDataSource,
    communityRepository: CommunityRepository,
    assignmentRepository: AssignmentRepository
) {
    routing {
        authRouting(authRepository)
        chatRouting(roomController,messageDataSource)
        postRouting(postRepository)
        materialRouting(materialRepository)
        replyRouting(replyRepository)
        communityRouting(communityRepository)
        assignmentRouting(assignmentRepository)

    }
}
