package com.purple.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.purple.core.database.dao.FeedDao
import com.purple.core.database.dao.RoomDao
import com.purple.core.database.dao.UserDao
import com.purple.core.database.entity.*
import com.purple.core.database.utils.DateConverters

@Database(
    entities = [
        MemberRoomEntity::class,
        MemberEntity::class,
        RoomCommonOptionsEntity::class,
        RoomEntity::class,
        QuestionEntity::class,
        QuestionRoomCrossEntity::class,
        FeedEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverters::class
)
abstract class HiDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun roomDao(): RoomDao
    abstract fun feedDao(): FeedDao
}
