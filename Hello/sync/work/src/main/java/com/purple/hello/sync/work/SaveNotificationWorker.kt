package com.purple.hello.sync.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.purple.core.database.dao.NotificationDao
import com.purple.core.database.entity.NotificationEntity
import javax.inject.Inject

class SaveNotificationWorker @Inject constructor(
    appContext: Context,
    workerParams: WorkerParameters,
    private val notificationDao: NotificationDao,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val title = inputData.getString("title") ?: ""
        val body = inputData.getString("body") ?: ""
        val time = inputData.getLong("time", 0L)

        saveNotificationToDatabase(title, body, time)

        return Result.success()
    }

    private suspend fun saveNotificationToDatabase(title: String, body: String, time: Long) {
        notificationDao.insertNotificationEntity(
            NotificationEntity(
                title = title,
                body = body,
                timestamp = time,
            ),
        )
    }

    companion object {
        const val KEY_TITLE = "title"
        const val KEY_BODY = "body"
        const val KEY_TIMESTAMP = "time"
    }
}
