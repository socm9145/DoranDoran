package com.purple.hello.core.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface AccountService {
    @GET("account/login/google")
    suspend fun loginWithGoogle(
        @Header("id-token") idToken: String,
    ): Response<Void>

    @GET("account/login/kakao")
    suspend fun loginWithKakao(
        @Header("id-token") accessToken: String,
    ): Response<Void>

    @GET("account/reissue")
    fun reIssue(
        @Header("Access_Token") accessToken: String,
        @Header("Refresh_Token") refreshToken: String,
    ): Response<AccountTokenResponse>

    @GET("account/logout")
    fun logout()
}
