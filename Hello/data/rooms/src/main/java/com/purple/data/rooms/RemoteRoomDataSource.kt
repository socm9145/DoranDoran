package com.purple.data.rooms

import com.purple.data.rooms.service.RoomService
import javax.inject.Inject

class RemoteRoomDataSource @Inject constructor(
    private val roomService: RoomService,
) : RoomDataSource {

    override suspend fun createRoom(roomName: String, userName: String, roomQuestion: String, roomPassword: String) =
        roomService.createRoom(roomName, userName, roomQuestion, roomPassword)
}
