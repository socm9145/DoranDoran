package com.purple.hello.data.account

import com.purple.hello.core.datastore.AccountDataStore
import com.purple.hello.core.datastore.UserDataStore
import com.purple.hello.core.network.AccountService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteAccountDataSource @Inject constructor(
    private val accountService: AccountService,
    private val accountDataStore: AccountDataStore,
    private val userDataStore: UserDataStore,
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

    suspend fun logout() {
        accountDataStore.clearToken()
        userDataStore.clearUserId()
    }
}
