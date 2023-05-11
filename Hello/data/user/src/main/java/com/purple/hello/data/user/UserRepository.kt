package com.purple.hello.data.user

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchUserInfo()

    fun getUserId(): Flow<Long>
}
