package com.purple.hello.domain.repo

import com.purple.hello.data.account.datasource.RemoteAccountDataSource
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val remoteAccountDataSource: RemoteAccountDataSource,
) {
    suspend fun loginWithGoogle(idToken: String) {
        remoteAccountDataSource.loginWithGoogle(idToken)
    }

    suspend fun loginWithKakao(accessToken: String) {
        remoteAccountDataSource.loginWithKakao(accessToken)
    }
}
