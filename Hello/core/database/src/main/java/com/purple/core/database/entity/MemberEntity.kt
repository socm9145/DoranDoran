package com.purple.core.database.entity

import androidx.room.Entity
import java.util.Date

@Entity(
    tableName = "members",
    primaryKeys = ["userId"],
)
data class MemberEntity(
    val birth: Date?,
    val userId: Long,
    val profileUrl: String?,
)
