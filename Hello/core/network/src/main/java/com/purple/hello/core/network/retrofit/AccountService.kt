package com.purple.hello.core.network.retrofit

import retrofit2.http.Header
import retrofit2.http.POST

interface AccountService {
    @POST("account/login/google")
    suspend fun loginWithGoogle(@Header("id_token") idToken: String): AccountTokenResponse

    @POST("account/login/kakao")
    suspend fun loginWithKakao(@Header("access_token") accessToken: String): AccountTokenResponse
}
