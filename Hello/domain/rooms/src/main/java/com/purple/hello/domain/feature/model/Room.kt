package com.purple.hello.domain.feature.model

data class Room(
    val id: Int,
    val name: String,
    val members: List<User>,
)

data class AdminRoomOptions(
    val passwordQuestion: String,
)
