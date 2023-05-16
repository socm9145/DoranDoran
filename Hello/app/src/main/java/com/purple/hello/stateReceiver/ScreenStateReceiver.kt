package com.purple.hello.stateReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.*

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
