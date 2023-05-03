package com.purple.hello.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AccountPreferencesDataSource @Inject constructor(
    private val accountPreferences: DataStore<AccountData>
) {
    val accessToken = accountPreferences.data.map { it.accessToken }
    val refreshToken = accountPreferences.data.map { it.refreshToken }

    suspend fun setToken(accessToken: String, refreshToken: String) {
        try {
            accountPreferences.updateData {
                it.copy {
                    this.accessToken = accessToken
                    this.refreshToken = refreshToken
                }
            }
        } catch (ioException: IOException) {
            Log.e("AccountPreferences", "Failed to update token")
        }
    }
}
