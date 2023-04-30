package com.purple.hello.data.account.datasource

import androidx.datastore.core.DataStore
import com.purple.hello.core.datastore.AccountData
import com.purple.hello.core.datastore.AccountDataSerializer.Companion.toAccountData
import com.purple.hello.core.network.AccountService
import javax.inject.Inject

class RemoteAccountDataSource @Inject constructor(
    private val accountService: AccountService,
    private val accountDataStore: DataStore<AccountData>,
) {

    suspend fun loginWithGoogle(idToken: String) {
        val accountTokenResponse = accountService.loginWithGoogle(idToken)
        val accountData = toAccountData(accountTokenResponse)
        accountDataStore.updateData { accountData }
    }

    suspend fun loginWithKakao(accessToken: String) {
        val accountTokenResponse = accountService.loginWithKakao(accessToken)
        val accountData = toAccountData(accountTokenResponse)
        accountDataStore.updateData { accountData }
    }
}
