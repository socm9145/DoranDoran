package com.purple.hello.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

object RetrofitApiClient {
    /* TODO : BASE_URL 수정 필요 */
    private const val BASE_URL = "http://example.com/"
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

    private fun getApiClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient(AppInterceptor()))
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("(header Key)", "(header Value)")
                .build()
            proceed(newRequest)
        }
    }

    val accountService: AccountService = getApiClient().create(AccountService::class.java)
}
