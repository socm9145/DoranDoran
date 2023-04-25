package com.purple.core.model

data class Room(
    val roomId: Long,
    val personalOptions: PersonalOptions,
    val members: List<User>,
)

data class PersonalOptions(
    val userRoomId: Long,
    val roomName: String,
    val userName: String,
)

data class CommonOptions(
    val passwordQuestion: String,
)
