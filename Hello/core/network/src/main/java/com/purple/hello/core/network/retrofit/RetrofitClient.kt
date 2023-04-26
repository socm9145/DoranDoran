package com.purple.hello.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType

object RetrofitClient {
    private const val BASE_URL = "http://example.com/"

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    private val contentType = MediaType.get("application/json")

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    val accountService: AccountService = retrofit.create(AccountService::class.java)
}
