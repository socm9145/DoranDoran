package com.purple.hello.data.account

import com.purple.hello.core.datastore.AccountPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val remoteAccountDataSource: RemoteAccountDataSource,
    private val accountDataStore: AccountPreferencesDataSource
) {
    suspend fun loginWithGoogle(idToken: String) {
        remoteAccountDataSource.loginWithGoogle(idToken)
    }

    suspend fun loginWithKakao(accessToken: String) {
        remoteAccountDataSource.loginWithKakao(accessToken)
    }

    suspend fun logout() {
        remoteAccountDataSource.logout()
    }

    fun isLoggedIn(): Flow<Boolean> = accountDataStore.isLoggedIn()
}
