package com.purple.core.model

data class Room(
    val roomId: Long,
    val roomName: String,
    val members: List<Member>,
)

data class PersonalOptions(
    val userRoomId: Long,
    val roomName: String,
    val userName: String,
)

data class CommonOptions(
    val passwordQuestion: String,
)
