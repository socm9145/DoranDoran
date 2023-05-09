package com.purple.hello.domain.rooms

import com.purple.data.rooms.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class GetQuestionUseCase @Inject constructor(
    private val feedRepository: FeedRepository,
) {
    suspend operator fun invoke(roomId: Long, date: Date): Flow<String> = flow {
        val dbValue = feedRepository.getQuestion(roomId, date).firstOrNull()

        if (dbValue != null) {
            emit(dbValue)
        } else {
            emit(feedRepository.updateQuestion(roomId, date))
        }
    }
}
