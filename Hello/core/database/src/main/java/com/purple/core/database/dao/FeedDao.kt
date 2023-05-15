package com.purple.core.database.dao

import androidx.room.*
import com.purple.core.database.entity.FeedEntity
import com.purple.core.database.entity.QuestionEntity
import com.purple.core.database.entity.QuestionRoomCrossEntity
import com.purple.core.database.model.FeedWithAuthor
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.*

@Dao
interface FeedDao {

    @Query(
        "SELECT content FROM question " +
            "INNER JOIN room_question_cross ON question.questionId = room_question_cross.questionId" +
            " WHERE room_question_cross.roomId = :roomId AND room_question_cross.date = :date",
    )
    fun getQuestionWithRoomIdAndDate(roomId: Long, date: LocalDateTime): Flow<String>

    @Insert
    fun insertQuestionRoomCrossEntity(crossEntity: QuestionRoomCrossEntity)

    @Insert
    fun insertQuestionEntity(questionEntity: QuestionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFeedEntity(feedEntity: FeedEntity)

    @Query(
        """
        SELECT *
        FROM feed
        INNER JOIN user_room_cross 
            ON feed.roomId = user_room_cross.roomId AND feed.userId = user_room_cross.userId
        INNER JOIN members 
            ON feed.userId = members.userId
        WHERE feed.roomId = :roomId AND DATE(feed.createAt) = DATE(:date)
        """,
    )
    fun getFeedWithRoomIdAndDate(roomId: Long, date: LocalDateTime): Flow<List<FeedWithAuthor>>

    @Query("SELECT COUNT(*) FROM feed WHERE roomId = :roomId AND DATE(createAt) = DATE(:date)")
    fun getCountOfFeedByDate(roomId: Long, date: LocalDateTime): Int
}
