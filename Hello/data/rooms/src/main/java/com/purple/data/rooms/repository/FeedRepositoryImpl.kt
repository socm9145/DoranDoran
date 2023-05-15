package com.purple.data.rooms.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.purple.core.database.dao.FeedDao
import com.purple.core.database.dao.RoomDao
import com.purple.core.database.entity.FeedEntity
import com.purple.core.database.entity.QuestionEntity
import com.purple.core.database.entity.QuestionRoomCrossEntity
import com.purple.core.database.model.FeedWithAuthor
import com.purple.core.database.model.asExternalModel
import com.purple.core.model.Feed
import com.purple.data.rooms.datasource.RemoteFeedDataSource
import com.purple.data.rooms.model.response.feed.asFeedEntity
import com.purple.hello.core.datastore.UserDataStore
import com.purple.hello.core.network.utils.toLocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.File
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedDao: FeedDao,
    private val roomDao: RoomDao,
    private val userDataStore: UserDataStore,
    private val remoteFeedDataSource: RemoteFeedDataSource,
) : FeedRepository {

    override fun getQuestion(roomId: Long, date: LocalDateTime): Flow<String> =
        feedDao.getQuestionWithRoomIdAndDate(roomId, date)

    override fun getDateFeed(roomId: Long, date: LocalDateTime): Flow<List<Feed>> {
        return feedDao.getFeedWithRoomIdAndDate(roomId, date).map {
            it.map(FeedWithAuthor::asExternalModel)
        }
    }

    override suspend fun updateQuestion(roomId: Long, date: LocalDateTime): String {
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

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateDateFeed(roomId: Long, date: LocalDateTime) {
        run {
            val response = remoteFeedDataSource.getDateFeed(roomId, date)
            if (response.isSuccessful) {
                val feedList = response.body() ?: emptyList()
                feedList.map { feed ->
                    feedDao.insertFeedEntity(
                        FeedEntity(
                            roomId = roomId,
                            userId = feed.userId,
                            feedId = feed.feedId,
                            createAt = feed.createdAt.toLocalDateTime() ?: date,
                            feedUrl = feed.feedUrl,
                            content = feed.content,
                        ),
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun uploadFeed(roomId: Long, feedImage: File) {
        val userRoomId = roomDao.getRoom(roomId).userRoomId
        val response = remoteFeedDataSource.postFeed(userRoomId, feedImage)
        val userId = runBlocking { userDataStore.userId.first() }

        if (response.isSuccessful) {
            val feed = response.body()
            if (feed != null) {
                feedDao.insertFeedEntity(
                    feed.asFeedEntity(roomId, userId),
                )
            }
        }
    }

    override suspend fun isFeedExistByDate(roomId: Long, date: LocalDateTime): Boolean =
        feedDao.getCountOfFeedByDate(roomId, date) != 0
}
