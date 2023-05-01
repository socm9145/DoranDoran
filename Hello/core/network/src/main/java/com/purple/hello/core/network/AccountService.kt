package com.purple.hello.core.network

import retrofit2.http.GET
import retrofit2.http.Header

interface AccountService {
    @GET("account/login/google")
    suspend fun loginWithGoogle(
        @Header("id_token") idToken: String,
    ): AccountTokenResponse

    @GET("account/login/kakao")
    suspend fun loginWithKakao(
        @Header("id_token") accessToken: String,
    ): AccountTokenResponse

    @GET("account/reissue")
    fun reIssue(
        @Header("Access_Token") accessToken: String,
        @Header("Refresh_Token") refreshToken: String,
    ): Result<AccountTokenResponse>

    @GET("account/logout")
    fun logout()
}
