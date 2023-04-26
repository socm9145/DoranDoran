package com.purple.hello.data.account.datasource

import com.purple.hello.core.network.retrofit.AccountService
import javax.inject.Inject

class RemoteAccountDataSource @Inject constructor(
    private val accountService: AccountService
) {
    suspend fun loginWithGoogle(idToken: String) {
        accountService.loginWithGoogle(idToken)
    }

    suspend fun loginWithKakao(accessToken: String) {
        accountService.loginWithKakao(accessToken)
    }
}
