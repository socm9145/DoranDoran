package com.purple.core.database.entity

import androidx.room.Entity

@Entity(
    tableName = "members",
    primaryKeys = ["userId"],
)
data class MemberEntity(
    val userId: Long,
    val profileUrl: String,
)
