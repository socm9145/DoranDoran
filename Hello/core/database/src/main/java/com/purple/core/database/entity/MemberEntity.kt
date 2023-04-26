package com.purple.core.database.entity

import androidx.room.Entity

@Entity(tableName = "members")
data class MemberEntity(
    val id: Long,
    val profileUrl: String,
)
