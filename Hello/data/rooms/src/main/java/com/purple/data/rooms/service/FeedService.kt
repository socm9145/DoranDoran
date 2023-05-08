package com.purple.data.rooms.service

import com.purple.data.rooms.model.QuestionResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

interface FeedService {

    @POST("room/date-question")
    suspend fun getDateQuestion(
        @Query("roomId") roomId: Long,
        @Query("date") date: Date,
    ): Response<QuestionResponse>
}
