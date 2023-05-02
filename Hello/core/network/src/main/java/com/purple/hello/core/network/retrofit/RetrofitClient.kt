package com.purple.hello.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

object RetrofitClient {
    /* TODO : BASE_URL 수정 필요 */
    private const val BASE_URL = "https://doeran.kr/api/"

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    private val contentType = MediaType.get("application/json")

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    fun getInstance(): Retrofit = retrofit

    val accountService: AccountService = retrofit.create(AccountService::class.java)
}
