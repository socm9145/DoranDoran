package com.purple.hello.stateReceiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.work.*

class ScreenStateService : Service() {
    private var screenStateReceiver: BroadcastReceiver? = null

    override fun onCreate() {
        super.onCreate()
        screenStateReceiver = ScreenStateReceiver()
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        registerReceiver(screenStateReceiver, intentFilter)

        val notification = createNotification()
        startForeground(ONGOING_NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(screenStateReceiver)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun createNotification(): Notification {
        val channelId = "screen_state_channel"
        val channelName = "Screen State Channel"

        // Create a notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(com.purple.hello.R.drawable.ic_dorandoran_notification)
            .setContentTitle("Screen State Service")
            .setContentText("Running in the background")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .build()
    }

    companion object {
        private const val ONGOING_NOTIFICATION_ID = 1
    }
}

class ScreenStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_SCREEN_ON -> { scheduleWorker(context, true) }
            Intent.ACTION_SCREEN_OFF -> { scheduleWorker(context, false) }
        }
    }

    private fun scheduleWorker(context: Context, screenState: Boolean) {
        val data = Data.Builder()
            .putBoolean(TimerWorker.INPUT_SCREEN_STATE, screenState)
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val screenStateRequest = OneTimeWorkRequestBuilder<TimerWorker>()
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniqueWork(
            "ScreenStateWorker",
            ExistingWorkPolicy.REPLACE,
            screenStateRequest
        )
    }
}
