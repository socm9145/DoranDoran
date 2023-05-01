package com.purple.hello

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.kakao.sdk.common.KakaoSdk
import com.purple.hello.core.datastore.AccountData
import com.purple.hello.core.datastore.AccountDataSerializer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }
}
