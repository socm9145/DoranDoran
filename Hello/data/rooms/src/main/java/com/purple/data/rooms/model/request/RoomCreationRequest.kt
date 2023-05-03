package com.purple.data.rooms.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RoomCreationRequest(
    val roomName: String,
    val userName: String,
    val question: String,
    val password: String,
)
