package com.purple.data.rooms.service

import com.purple.data.rooms.model.MembersResponse
import com.purple.data.rooms.model.RoomCreationResponse
import com.purple.data.rooms.model.RoomListResponse
import com.purple.data.rooms.model.request.*
import retrofit2.Response
import retrofit2.http.*

interface RoomService {

    @GET("user/rooms")
    suspend fun getRoomList(): Response<List<RoomListResponse>>

    @POST("room/create")
    suspend fun createRoom(
        @Body requestForCreate: RoomCreationRequest,
    ): Response<RoomCreationResponse>

    @GET("room/user-list")
    suspend fun getMembersInRoom(
        @Query("roomId") roomId: Long,
    ): Response<MembersResponse>

    @GET("room/code")
    suspend fun getRoomCode(
        @Query("roomId") roomId: Long,
    ): Response<String>

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
