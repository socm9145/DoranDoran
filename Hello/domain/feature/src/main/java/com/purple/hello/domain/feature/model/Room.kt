package com.purple.hello.domain.feature.model

data class Room(
    val id: Int,
    val option: RoomOption,
    val members: List<User>,
)

data class RoomOption(
    val roomName: String,
    val passwordQuestion: String,
    val password: String,
)

data class User(
    val id: Int,
    val name: String,
    val profileUrl: String,
)
