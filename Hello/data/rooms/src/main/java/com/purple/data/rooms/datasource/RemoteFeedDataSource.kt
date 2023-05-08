package com.purple.data.rooms.datasource

import com.purple.data.rooms.model.QuestionResponse
import com.purple.data.rooms.service.FeedService
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class RemoteFeedDataSource @Inject constructor(
    private val feedService: FeedService,
) : FeedDataSource {

    override suspend fun getDateQuestion(roomId: Long, date: Date): Response<QuestionResponse> =
        feedService.getDateQuestion(roomId, date)
}
