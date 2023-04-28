package com.purple.hello.core.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.purple.hello.core.network.AccountTokenResponse
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class AccountDataSerializer @Inject constructor() : Serializer<AccountData> {
    override val defaultValue: AccountData = AccountData.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AccountData {
        try {
            return AccountData.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("can not read proto", exception)
        }
    }

    override suspend fun writeTo(t: AccountData, output: OutputStream) {
        t.writeTo(output)
    }

    companion object {
        fun toAccountData(tokenResponse: AccountTokenResponse): AccountData {
            return AccountData.newBuilder()
                .setAccessToken(tokenResponse.accessToken)
                .setRefreshToken(tokenResponse.refreshToken)
                .build()
        }
    }
}

private val Context.accountDataStore: DataStore<AccountData> by dataStore(
    fileName = "account_data.pb",
    serializer = AccountDataSerializer()
)
