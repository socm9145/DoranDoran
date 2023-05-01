package com.purple.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.purple.core.database.dao.RoomDao
import com.purple.core.database.dao.UserDao
import com.purple.core.database.entity.*

@Database(
    entities = [
        MemberRoomEntity::class,
        MemberEntity::class,
        RoomCommonOptionsEntity::class,
        RoomEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class HiDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun roomDao(): RoomDao
}
