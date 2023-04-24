package com.purple.hello

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class HiApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }
}
