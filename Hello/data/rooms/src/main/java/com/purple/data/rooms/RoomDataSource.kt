package com.purple.data.rooms

import com.purple.data.rooms.model.RoomCreationResponse
import retrofit2.Response

interface RoomDataSource {

    suspend fun createRoom(
        roomName: String,
        userName: String,
        roomQuestion: String,
        roomPassword: String,
    ): Response<RoomCreationResponse>
}
