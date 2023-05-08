package com.purple.data.rooms.datasource

import com.purple.data.rooms.model.RoomCreationResponse
import retrofit2.Response

interface RoomDataSource {

    suspend fun createRoom(
        roomName: String,
        userName: String,
        roomQuestion: String,
        roomPassword: String,
    ): Response<RoomCreationResponse>

    suspend fun updateRoomName(
        userRoomId: Long,
        roomName: String,
    ): Response<Void>

    suspend fun updateUserName(
        userRoomId: Long,
        userName: String,
    ): Response<Void>

    suspend fun updatePassword(
        roomId: Long,
        password: String,
        question: String,
    ): Response<Void>

    suspend fun exitRoom(
        userRoomId: Long,
    ): Response<Void>

    suspend fun deleteRoom(
        roomId: Long,
    ): Response<Void>
}
