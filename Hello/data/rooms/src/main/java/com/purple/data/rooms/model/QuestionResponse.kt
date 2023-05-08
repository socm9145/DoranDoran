package com.purple.data.rooms.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionResponse(
    val questionId: Long,
    val content: String,
)
