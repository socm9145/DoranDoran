package com.purple.data.rooms

import com.purple.core.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getRooms(): Flow<List<Room>>
    suspend fun createRoom(
        roomName: String,
        userName: String,
        roomQuestion: String,
        roomPassword: String,
    )
    fun joinRoom(roomCode: Int)
    fun updateUserName(userRoomId: Long, userName: String)
    fun updateRoomName(userRoomId: Long, roomName: String)
    fun updatePassword(roomId: Int, passwordQuestion: String, password: String)
    fun leaveRoom(roomId: Int)
    fun deleteRoom(roomId: Int)
}
