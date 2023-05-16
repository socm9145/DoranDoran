package com.purple.hello.stateReceiver

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*

class TimerWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private var timer: Timer? = null

    override fun doWork(): Result {
        val screenState = inputData.getBoolean(INPUT_SCREEN_STATE, false)

        if (screenState) {
            // 화면이 켜진 경우
            stopTimer()
        } else {
            // 화면이 꺼진 경우
            startTimer()
        }

        return Result.success()
    }

    private fun startTimer() {
        // 타이머 시작
        Log.d("TimerWorker", "타이머 시작")

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                // 타이머가 갱신될 때마다 호출되는 콜백
                Log.d("TimerWorker", "타이머 작동 중")
                // 여기에 수행할 작업을 추가하세요.
            }
        }, 0, 1000) // 1초마다 작업을 반복합니다.
    }

    private fun stopTimer() {
        // 타이머 중지
        Log.d("TimerWorker", "타이머 중지")
        timer?.cancel()
        timer = null
    }

    private fun sendDataToServer() {
        // TODO: 서버로 데이터 전송하는 코드를 작성
        // 데이터를 서버로 전송하는 HTTP 요청을 보내는 로직을 구현해야 합니다.
        // 예를 들어, HttpURLConnection 또는 OkHttp를 사용하여 POST 요청을 보낼 수 있습니다.
        Log.d("TimerWorker", "무응답!")
    }

    companion object {
        const val INPUT_SCREEN_STATE = "input_screen_state"
    }
}
