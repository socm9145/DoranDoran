package com.purple.hello.data.user

import retrofit2.Response

interface UserDataSource {

    suspend fun getUserInfo(): Response<UserResponse>
}
