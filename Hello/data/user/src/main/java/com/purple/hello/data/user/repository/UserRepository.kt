package com.purple.hello.data.user.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchUserInfo()

    fun getUserId(): Flow<Long>

    suspend fun setProfile(profileUrl: String, birth: String)
}
