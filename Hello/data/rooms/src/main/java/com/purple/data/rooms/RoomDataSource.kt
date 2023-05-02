package com.purple.data.rooms

import com.purple.data.rooms.model.RoomCreationResponse

interface RoomDataSource {

    suspend fun createRoom(
        roomName: String,
        userName: String,
        roomQuestion: String,
        roomPassword: String,
    ): RoomCreationResponse
}
