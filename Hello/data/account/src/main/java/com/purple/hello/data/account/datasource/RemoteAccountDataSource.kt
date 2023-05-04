package com.purple.hello.data.account.datasource

import com.purple.hello.core.datastore.AccountPreferencesDataSource
import com.purple.hello.core.network.AccountService
import javax.inject.Inject

class RemoteAccountDataSource @Inject constructor(
    private val accountService: AccountService,
    private val accountDataStore: AccountPreferencesDataSource,
) {

    suspend fun loginWithGoogle(idToken: String) {
        val responseHeader = accountService.loginWithGoogle(idToken).headers()
        accountDataStore.setToken(
            responseHeader["Access-Token"] ?: "",
            responseHeader["Refresh-Token"] ?: "",
        )
    }

    suspend fun loginWithKakao(accessToken: String) {
        val responseHeader = accountService.loginWithKakao(accessToken).headers()
        accountDataStore.setToken(
            responseHeader["Access-Token"] ?: "",
            responseHeader["Refresh-Token"] ?: "",
        )
    }
}
