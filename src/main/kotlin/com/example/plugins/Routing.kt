package com.example.plugins

import com.example.repository.AuthRepository
import com.example.repository.MaterialRepository
import com.example.repository.PostRepository
import com.example.repository.ReplyRepository
import com.example.room.RoomController
import com.example.routes.*
import com.example.security.TokenService
import com.example.security.hashing.HashingService
import com.example.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting2(
    hashingService: HashingService,
    authRepository: AuthRepository,
    postRepository: PostRepository,
    tokenService: TokenService,
    tokenConfig: TokenConfig,
    roomController: RoomController,
    materialRepository: MaterialRepository,
    replyRepository: ReplyRepository
) {

    routing {
        signUp2(
            hashingService = hashingService,
            authRepository = authRepository,
            tokenService = tokenService,
            tokenConfig = tokenConfig
        )

        signIn2(
            authRepository = authRepository,
            tokenService = tokenService,
            hashingService = hashingService,
            tokenConfig = tokenConfig
        )
        createPost2(
            postRepository = postRepository
        )
        getAllPosts2(
            postRepository = postRepository
        )
        upVotePost2(
            postRepository = postRepository
        )
        downVotePost2(
            postRepository = postRepository
        )
        deletePost2(
            postRepository = postRepository
        )
        updatePost2(
            postRepository = postRepository
        )
        getSignedUser2(
            authRepository = authRepository
        )
        home2()
        getAllMessages2(roomController)
//        chatRoute2(roomController)
        materialFiles(
            materialRepository = materialRepository
        )
        createReply(
            replyRepository = replyRepository
        )
        fetchReplies(
            replyRepository = replyRepository
        )
        updateReply(
            replyRepository = replyRepository
        )
        deleteReply(
            replyRepository = replyRepository
        )
        upvoteReply(
            replyRepository = replyRepository
        )
        downvoteReply(
            replyRepository = replyRepository
        )
//        reportReply(
//            replyRepository = replyRepository
//        )
    }

}
