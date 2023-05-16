package com.purple.hello.stateReceiver

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class TimerWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        sendDataToServer()
        return Result.success()
    }

    private fun sendDataToServer() {
        // TODO: 서버로 데이터 전송하는 코드를 작성
        // 데이터를 서버로 전송하는 HTTP 요청을 보내는 로직을 구현해야 합니다.
        // 예를 들어, HttpURLConnection 또는 OkHttp를 사용하여 POST 요청을 보낼 수 있습니다.
        Log.d("TimerWorker", "무응답!")
    }
}
