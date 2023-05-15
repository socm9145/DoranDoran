package com.purple.hello.domain.rooms.feed

import com.purple.data.rooms.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetQuestionUseCase @Inject constructor(
    private val feedRepository: FeedRepository,
) {
    suspend operator fun invoke(roomId: Long, date: LocalDateTime): Flow<String?> = flow {
        val dateQuestion = feedRepository.getQuestion(roomId, date).first()

        if(dateQuestion == null) {
            feedRepository.updateQuestion(roomId, date)
            emit(null)
        } else {
            emit(dateQuestion)
        }
    }

}
