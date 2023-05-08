package com.purple.hello.core.network

import androidx.datastore.core.DataStore
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.purple.hello.core.datastore.AccountData
import com.purple.hello.core.datastore.AccountPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitApiClient {
    /* TODO : BASE_URL 체크 필요 */
    private const val BASE_URL = "https://www.doeran.kr/api/"
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    private val contentType = "application/json".toMediaType()

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            ).build()
    }

    @Provides
    @Singleton
    fun provideAppInterceptor(accountDataStore: DataStore<AccountData>): AppInterceptor {
        return AppInterceptor(
            AccountPreferencesDataSource(accountDataStore),
        )
    }

    @Provides
    @Singleton
    @Named("default")
    fun provideDefaultRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    @Named("login")
    fun provideLoginRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    class AppInterceptor @Inject constructor(
        private val accountDataStore: AccountPreferencesDataSource,
    ) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val accessToken = runBlocking { accountDataStore.accessToken.first() }

            val tokenAddedRequest = request().newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
            val response = proceed(tokenAddedRequest)

            when (response.code) {
                401 -> handleUnauthorizedRequest(chain)
                else -> response
            }
        }

        private fun handleUnauthorizedRequest(
            chain: Interceptor.Chain,
        ): Response = with(chain) {
            val accessToken = runBlocking { accountDataStore.accessToken.first() }
            val refreshToken = runBlocking { accountDataStore.refreshToken.first() }
            val reIssueResponse = accountService.reIssue(accessToken, refreshToken)
            if (reIssueResponse.isSuccessful) {
                run {
                    runBlocking {
                        accountDataStore.setToken(
                            reIssueResponse.headers()["Access-Token"] ?: "",
                            reIssueResponse.headers()["Refresh-Token"] ?: "",
                        )
                    }
                    val newRequest = request().newBuilder()
                        .addHeader("Authorization", "Bearer ${reIssueResponse.headers()["Access-Token"]}")
                        .build()
                    proceed(newRequest)
                }
            } else {
                runBlocking { accountDataStore.clearToken() }
                proceed(request())
            }
        }
    }

    private val accountService: AccountService = provideLoginRetrofit().create(AccountService::class.java)
}
