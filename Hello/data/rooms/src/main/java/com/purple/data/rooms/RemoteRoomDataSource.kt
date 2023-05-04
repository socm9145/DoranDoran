package com.purple.data.rooms

import com.purple.data.rooms.model.request.*
import com.purple.data.rooms.service.RoomService
import retrofit2.Response
import javax.inject.Inject

class RemoteRoomDataSource @Inject constructor(
    private val roomService: RoomService,
) : RoomDataSource {

    override suspend fun createRoom(roomName: String, userName: String, roomQuestion: String, roomPassword: String) =
        roomService.createRoom(
            RoomCreationRequest(roomName, userName, roomQuestion, roomPassword),
        )

    override suspend fun updateRoomName(userRoomId: Long, roomName: String) =
        roomService.updateRoomName(
            RoomNameUpdateRequest(userRoomId, roomName),
        )

    override suspend fun updateUserName(userRoomId: Long, userName: String) =
        roomService.updateUserName(
            UserNameUpdateRequest(userRoomId, userName),
        )

    override suspend fun updatePassword(roomId: Long, password: String, question: String): Response<Void> =
        roomService.updatePassword(
            RoomPasswordUpdateRequest(roomId, password, question),
        )

    override suspend fun exitRoom(userRoomId: Long) =
        roomService.exitRoom(
            ExitRoomRequest(userRoomId),
        )

    override suspend fun deleteRoom(roomId: Long) =
        roomService.deleteRoom(
            DeleteRoomRequest(roomId),
        )
}
