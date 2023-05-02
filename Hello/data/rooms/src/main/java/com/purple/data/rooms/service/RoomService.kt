package com.purple.data.rooms.service

import com.purple.data.rooms.model.RoomCreationResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RoomService {

    @FormUrlEncoded
    @POST("room/create")
    suspend fun createRoom(
        @Field("roomName") roomName: String,
        @Field("userName") userName: String,
        @Field("roomQuestion") question: String,
        @Field("roomPassword") password: String,
    ): RoomCreationResponse
}
