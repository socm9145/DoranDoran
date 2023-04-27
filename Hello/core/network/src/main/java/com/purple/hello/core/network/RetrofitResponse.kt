package com.purple.hello.core.network

import kotlinx.serialization.Serializable

@Serializable
data class AccountTokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
