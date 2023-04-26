package com.purple.hello.core.network.retrofit

import retrofit2.http.POST
import retrofit2.http.Query

interface AccountService {
    @POST("account/login/google")
    suspend fun loginWithGoogle(@Query("id_token") idToken: String)

    @POST("account/login/kakao")
    suspend fun loginWithKakao(@Query("access_token") accessToken: String)
}
