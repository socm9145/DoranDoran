package com.purple.core.model

data class Feed(
    val id: Int,
    val headerImageUrl: String,
    val content: String,
    val author: Member,
)
