package com.purple.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(
    tableName = "room_common_option",
    primaryKeys = ["roomId"],
    foreignKeys = [
        ForeignKey(
            entity = RoomEntity::class,
            parentColumns = ["roomId"],
            childColumns = ["roomId"],
            onDelete = CASCADE,
        ),
    ],
)
data class RoomCommonOptionsEntity(
    val roomId: Long,
    val passwordQuestion: String,
    val password: String,
)
