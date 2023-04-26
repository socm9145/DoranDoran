package com.purple.data.rooms.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRoomDTO(
    @SerialName("members")
    val members: List<Member>,
    @SerialName("roomId")
    val roomId: Int,
    @SerialName("roomName")
    val roomName: String,
    @SerialName("userRoomId")
    val userRoomId: Int,
) {
    @Serializable
    data class Member(
        @SerialName("name")
        val name: String,
        @SerialName("profileUrl")
        val profileUrl: String,
    )
}
