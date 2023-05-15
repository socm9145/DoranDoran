package com.purple.core.model

import java.time.LocalDateTime

data class Member(
    val id: Long,
    val nickName: String,
    val profileUrl: String?,
)

data class Profile(
    val profileUrl: String?,
    val birth: LocalDateTime?,
)
