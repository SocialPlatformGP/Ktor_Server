package com.example.data.models.assignment

import kotlinx.serialization.Serializable

@Serializable
data class Assignment(
    val title: String = "",
    val description: String = "",
    val id: String = "",
    val attachments: List<AssignmentAttachment> = emptyList(),
    val maxPoints: Int = 10,
    val dueDate: Long = 0,
    val acceptLateSubmissions: Boolean = false,
    val createdAt: Long = 0,
    val creatorId: String = "",
    val communityId: String = "",
)
