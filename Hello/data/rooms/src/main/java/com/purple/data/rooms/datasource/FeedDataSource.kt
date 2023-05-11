package com.purple.data.rooms.datasource

import com.purple.data.rooms.model.CreateFeedResponse
import com.purple.data.rooms.model.QuestionResponse
import retrofit2.Response
import java.io.File
import java.time.LocalDateTime

interface FeedDataSource {

    suspend fun getDateQuestion(
        roomId: Long,
        date: LocalDateTime,
    ): Response<QuestionResponse>

    suspend fun postFeed(
        userRoomId: Long,
        image: File,
    ): Response<CreateFeedResponse>
}
