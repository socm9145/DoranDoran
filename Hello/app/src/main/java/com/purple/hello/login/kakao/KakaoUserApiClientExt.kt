package com.purple.hello.login

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

// 카카오톡 설치 체크
suspend fun UserApiClient.Companion.loginWithKakao(context: Context): OAuthToken {
    return if (instance.isKakaoTalkLoginAvailable(context)) {
        try {
            // 카카오톡 로그인 시도
            UserApiClient.loginWithKakaoTalk(context)
        } catch (error: Throwable) {
            // 사용자 취소
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                return UserApiClient.loginWithKakaoTalk(context)
            }
            // 그 외의 경우, 카카오계정으로 로그인 시도
            UserApiClient.loginWithKakaoAccount(context)
        }
    } else {
        UserApiClient.loginWithKakaoAccount(context)
    }
}

// 카카오톡 로그인 시도
suspend fun UserApiClient.Companion.loginWithKakaoTalk(context: Context): OAuthToken {
    return suspendCancellableCoroutine<OAuthToken> { continuation ->
        instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "카카오 로그인 실패", error)
                continuation.resumeWithException(error)
            } else if (token != null) {
                Log.i(ContentValues.TAG, "카카오 로그인 성공 ${token.accessToken}")
                continuation.resume(token)
            } else {
                Log.e(ContentValues.TAG, "카카오 로그인 실패")
                continuation.resumeWithException(RuntimeException("kakao access token을 받아오는데 실패함, 이유는 명확하지 않음."))
            }
        }
    }
}

// 카카오계정으로 로그인 시도
suspend fun UserApiClient.Companion.loginWithKakaoAccount(context: Context): OAuthToken {
    return suspendCancellableCoroutine<OAuthToken> { continuation ->
        instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "카카오 로그인 실패", error)
                continuation.resumeWithException(error)
            } else if (token != null) {
                Log.i(ContentValues.TAG, "카카오 로그인 성공 ${token.accessToken}")
                continuation.resume(token)
            } else {
                Log.e(ContentValues.TAG, "카카오 로그인 실패")
                continuation.resumeWithException(RuntimeException("kakao access token을 받아오는데 실패함, 이유는 명확하지 않음."))
            }
        }
    }
}
