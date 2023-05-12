package com.purple.hello.domain.rooms

import com.purple.core.model.Feed
import com.purple.data.rooms.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetDateFeedUseCase @Inject constructor(
    private val feedRepository: FeedRepository,
) {
    operator fun invoke(roomId: Long, date: LocalDateTime): Flow<List<Feed>> =
        feedRepository.getDateFeed(roomId, date)
}
