package com.purple.hello.data.user

import retrofit2.Response
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val userService: UserService,
) : UserDataSource {

    override suspend fun getUserInfo(): Response<UserResponse> =
        userService.getUserInfo()
}
