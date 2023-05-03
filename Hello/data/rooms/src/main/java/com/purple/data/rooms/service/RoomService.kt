package com.purple.data.rooms.service

import com.purple.data.rooms.model.RoomCreationResponse
import com.purple.data.rooms.model.request.RoomCreationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RoomService {

    @POST("room/create")
    suspend fun createRoom(
        @Body requestForCreate: RoomCreationRequest,
    ): Response<RoomCreationResponse>
}
