package com.purple.data.rooms.service

import com.purple.data.rooms.model.CreateFeedResponse
import com.purple.data.rooms.model.QuestionResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.util.*

interface FeedService {

    @POST("room/date-question")
    suspend fun getDateQuestion(
        @Query("roomId") roomId: Long,
        @Query("date") date: Date,
    ): Response<QuestionResponse>

    @Multipart
    @POST("room/feed")
    suspend fun postFeed(
        @Part("content") content: String?,
        @Part feedFile: MultipartBody.Part,
        @Part("feedType") feedType: String?,
        @Part("feedUrl") feedUrl: String?,
        @Part("userRoomId") userRoomId: Int,
    ): Response<CreateFeedResponse>
}
