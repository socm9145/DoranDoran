package com.purple.data.rooms

import com.purple.core.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getRooms(): Flow<List<Room>>
    fun getRoom(roomId: Long): Flow<Room>
    suspend fun createRoom(
        roomName: String,
        userName: String,
        roomQuestion: String,
        roomPassword: String,
    )
    fun joinRoom(roomCode: Int)
    suspend fun updateUserName(userRoomId: Long, userName: String)
    suspend fun updateRoomName(userRoomId: Long, roomName: String)
    suspend fun updatePassword(roomId: Long, passwordQuestion: String, password: String)
    suspend fun leaveRoom(userRoomId: Long)
    suspend fun deleteRoom(roomId: Long)
}
