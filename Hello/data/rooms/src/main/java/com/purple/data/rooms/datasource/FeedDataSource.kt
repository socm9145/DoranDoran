package com.purple.data.rooms.datasource

import com.purple.data.rooms.model.CreateFeedResponse
import com.purple.data.rooms.model.QuestionResponse
import retrofit2.Response
import java.io.File
import java.util.*

interface FeedDataSource {

    suspend fun getDateQuestion(
        roomId: Long,
        date: Date,
    ): Response<QuestionResponse>

    suspend fun postFeed(
        userRoomId: Long,
        image: File,
    ): Response<CreateFeedResponse>
}
