package com.purple.data.rooms.datasource

import com.purple.data.rooms.model.QuestionResponse
import retrofit2.Response
import java.util.*

interface FeedDataSource {

    suspend fun getDateQuestion(
        roomId: Long,
        date: Date,
    ): Response<QuestionResponse>
}
