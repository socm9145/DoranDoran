package com.purple.hello.core.network

import android.app.Application
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import com.purple.hello.core.datastore.AccountDataSerializer.Companion.getAccessToken
import com.purple.hello.core.datastore.AccountDataSerializer.Companion.getRefreshToken
import com.purple.hello.core.datastore.AccountDataSerializer.Companion.toAccountData
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
object RetrofitApiClient {
    /* TODO : BASE_URL 체크 필요 */
    private const val BASE_URL = "https://doeran.com/"
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    private val contentType = "application/json".toMediaType()

    private fun okHttpClient(interceptor: AppInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            ).build()
    }

    private fun getApiClient(application: Application): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient(AppInterceptor(application)))
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    class AppInterceptor @Inject constructor(
        private val context: Application,
    ) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val accessToken = getAccessToken(context)

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
            val accessToken = getAccessToken(context)
            val refreshToken = getRefreshToken(context)
            val reIssueResponse = accountService.reIssue(accessToken, refreshToken)
            when {
                reIssueResponse.isSuccess -> {
                    reIssueResponse.getOrNull().let {
                        when(it) {
                            null -> {
                                accountService.logout()
                                proceed(request())
                            }
                            else -> {
                                toAccountData(it.accessToken, it.refreshToken)
                                val newRequest = request().newBuilder()
                                    .addHeader("Authorization", "Bearer ${it.accessToken}")
                                    .build()
                                proceed(newRequest)
                            }
                        }
                    }
                }
                else -> {
                    accountService.logout()
                    proceed(request())
                }
            }
        }
    }

    private fun loginClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    private val accountService: AccountService = loginClient().create(AccountService::class.java)
}
