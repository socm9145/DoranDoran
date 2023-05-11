package com.purple.core.database.dao

import androidx.room.*
import com.purple.core.database.entity.FeedEntity
import com.purple.core.database.entity.QuestionEntity
import com.purple.core.database.entity.QuestionRoomCrossEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface FeedDao {

    @Query(
        "SELECT content FROM question " +
            "INNER JOIN room_question_cross ON question.questionId = room_question_cross.questionId" +
            " WHERE room_question_cross.roomId = :roomId AND room_question_cross.date = :date",
    )
    fun getQuestionWithRoomIdAndDate(roomId: Long, date: Date): Flow<String>

    @Insert
    fun insertQuestionRoomCrossEntity(crossEntity: QuestionRoomCrossEntity)

    @Insert
    fun insertQuestionEntity(questionEntity: QuestionEntity)

    @Insert
    fun insertFeedEntity(feedEntity: FeedEntity)
}
