package com.purple.hello.stateReceiver

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.purple.hello.domain.user.SendSafeAlarmUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TimerWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val sendSafeAlarm: SendSafeAlarmUseCase,
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        sendSafeAlarm
        return Result.success()
    }
}
