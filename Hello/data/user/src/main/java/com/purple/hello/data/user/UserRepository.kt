package com.purple.hello.data.user

interface UserRepository {

    suspend fun getUserInfo()
}
