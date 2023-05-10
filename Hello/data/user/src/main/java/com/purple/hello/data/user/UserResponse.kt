package com.purple.hello.data.user

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

// TODO: 체크 필요
@Serializable
data class UserResponse(
    val birth: @Contextual Date?,
    val userId: Long,
    @SerialName("profileURL") val profileUrl: String?,
)
