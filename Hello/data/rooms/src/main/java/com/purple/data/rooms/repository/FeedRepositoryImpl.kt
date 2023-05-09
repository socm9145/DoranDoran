package com.purple.data.rooms.repository

import com.purple.core.database.dao.FeedDao
import com.purple.core.database.entity.QuestionEntity
import com.purple.core.database.entity.QuestionRoomCrossEntity
import com.purple.data.rooms.datasource.RemoteFeedDataSource
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedDao: FeedDao,
    private val remoteFeedDataSource: RemoteFeedDataSource,
) : FeedRepository {

    override fun getQuestion(roomId: Long, date: Date): Flow<String> =
        feedDao.getQuestionWithRoomIdAndDate(roomId, date)

    override suspend fun updateQuestion(roomId: Long, date: Date): String {
        val response = remoteFeedDataSource.getDateQuestion(roomId, date)

        if (response.isSuccessful) {
            val question = response.body()
            if (question != null) {
                feedDao.insertQuestionRoomCrossEntity(
                    QuestionRoomCrossEntity(
                        questionId = question.questionId,
                        date = date,
                        roomId = roomId,
                    ),
                )
                feedDao.insertQuestionEntity(
                    QuestionEntity(
                        questionId = question.questionId,
                        content = question.content,
                    ),
                )
                return question.content
            } else {
                return "아직 질문이 없어요"
            }
        } else {
            return "네트워크 문제가 발생했습니다.."
        }
    }
}
