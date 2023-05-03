package com.purple.hello.data.account.datasource

import com.purple.hello.core.datastore.AccountPreferencesDataSource
import com.purple.hello.core.network.AccountService
import javax.inject.Inject

class RemoteAccountDataSource @Inject constructor(
    private val accountService: AccountService,
    private val accountDataStore: AccountPreferencesDataSource,
) {

    suspend fun loginWithGoogle(idToken: String) {
        val accountTokenResponse = accountService.loginWithGoogle(idToken)
        accountDataStore.setToken(accountTokenResponse.accessToken, accountTokenResponse.refreshToken)
    }

    suspend fun loginWithKakao(accessToken: String) {
        val accountTokenResponse = accountService.loginWithKakao(accessToken)
        accountDataStore.setToken(accountTokenResponse.accessToken, accountTokenResponse.refreshToken)
    }
}
