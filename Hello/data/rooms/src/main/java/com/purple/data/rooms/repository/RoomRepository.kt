package com.purple.data.rooms.repository

import com.purple.core.model.InputData
import com.purple.core.model.JoinRoomInputValue
import com.purple.core.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getRooms(): Flow<List<Room>>
    fun getRoom(roomId: Long): Flow<Room>
    suspend fun getRoomCode(roomId: Long): String
    suspend fun getJoinRoomData(roomId: Long): JoinRoomInputValue
    suspend fun createRoom(
        roomName: String,
        userName: String,
        roomQuestion: String,
        roomPassword: String,
    ): Long
    suspend fun fetchMembersInRoom(
        roomId: Long,
    )
    suspend fun fetchRooms()
    suspend fun joinRoom(roomId: Long, joinRoomInputValue: JoinRoomInputValue): String

    suspend fun getRoomSettings(roomId: Long)
    suspend fun updateUserName(userRoomId: Long, userName: String)
    suspend fun updateRoomName(userRoomId: Long, roomName: String)
    suspend fun updatePassword(roomId: Long, passwordQuestion: String, password: String)
    suspend fun leaveRoom(userRoomId: Long)
    suspend fun deleteRoom(roomId: Long)
}