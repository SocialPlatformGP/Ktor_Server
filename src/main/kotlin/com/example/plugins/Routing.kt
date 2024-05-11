package com.example.plugins

import com.example.repository.assignment.AssignmentRepository
import com.example.repository.calendar.CalendarRepository
import com.example.repository.community.CommunityRepository
import com.example.repository.grade.GradesRepository
import com.example.repository.material.MaterialRepository
import com.example.repository.post.PostRepository
import com.example.repository.reply.ReplyRepository
import com.example.repository.user.AuthRepository
import com.example.routes.assignment.assignmentRouting
import com.example.routes.auth.authRouting
import com.example.routes.calendar.calendarRouting
import com.example.routes.community.communityRouting
import com.example.routes.grades.gradesRouting
import com.example.routes.material.materialRouting
import com.example.routes.post.postRouting
import com.example.routes.reply.replyRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    authRepository: AuthRepository,
    postRepository: PostRepository,
    materialRepository: MaterialRepository,
    replyRepository: ReplyRepository,
    communityRepository: CommunityRepository,
    assignmentRepository: AssignmentRepository,
    calendarRepository: CalendarRepository,
    gradesRepository: GradesRepository
) {
    routing {
        authRouting(authRepository)
        postRouting(postRepository)
        materialRouting(materialRepository)
        replyRouting(replyRepository)
        communityRouting(communityRepository)
        assignmentRouting(assignmentRepository,communityRepository)
        calendarRouting(calendarRepository)
        gradesRouting(gradesRepository)
    }
}
