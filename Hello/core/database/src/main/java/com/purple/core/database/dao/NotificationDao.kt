package com.purple.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.purple.core.database.entity.NotificationEntity

@Dao
interface NotificationDao {
    @Insert
    suspend fun insertNotificationEntity(notificationEntity: NotificationEntity)
}
