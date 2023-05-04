package com.purple.data.rooms.service

import com.purple.data.rooms.model.RoomCreationResponse
import com.purple.data.rooms.model.request.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT

interface RoomService {

    @POST("room/create")
    suspend fun createRoom(
        @Body requestForCreate: RoomCreationRequest,
    ): Response<RoomCreationResponse>

    @PUT("room/name")
    suspend fun updateRoomName(
        @Body requestForUpdateRoomName: RoomNameUpdateRequest,
    ): Response<Void>

    @PUT("room/user")
    suspend fun updateUserName(
        @Body requestForUpdateUserName: UserNameUpdateRequest,
    ): Response<Void>

    @PUT("room/password")
    suspend fun updatePassword(
        @Body requestForUpdatePassword: RoomPasswordUpdateRequest,
    ): Response<Void>

    @PUT("room/exit")
    suspend fun exitRoom(
        @Body requestForExitRoom: ExitRoomRequest,
    ): Response<Void>

    @DELETE("room")
    suspend fun deleteRoom(
        @Body requestForDeleteRoom: DeleteRoomRequest,
    ): Response<Void>
}
