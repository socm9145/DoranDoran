package com.purple.hello.data.user.repository

interface UserRepository {

    suspend fun fetchUserInfo()

    suspend fun setProfile(profileUrl: String, birth: String)
}
