package com.purple.hello.data.user

import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("user/user-info")
    suspend fun getUserInfo(): Response<UserResponse>
}
