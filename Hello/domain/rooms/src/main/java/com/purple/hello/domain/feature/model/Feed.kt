package com.purple.hello.domain.feature.model

data class Feed(
    val id: Int,
    val headerImageUrl: String,
    val content: String,
    val author: User,
)
