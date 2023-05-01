package com.purple.data.rooms

import com.purple.core.model.CommonOptions
import com.purple.core.model.PersonalOptions
import com.purple.core.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getRooms(): Flow<List<Room>>
    fun createRoom(personalOptions: PersonalOptions, commonOptions: CommonOptions, password: String)
    fun joinRoom(roomCode: Int)
    fun updateUserName(userName: String)
    fun updateRoomName(roomName: String)
    fun updatePassword(passwordQuestion: String, password: String)
    fun leaveRoom(roomId: Int)
    fun deleteRoom(roomId: Int)
}
