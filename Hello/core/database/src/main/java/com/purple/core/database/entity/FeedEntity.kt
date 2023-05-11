package com.purple.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import java.util.*

@Entity(
    tableName = "feed",
    primaryKeys = ["roomId", "userId", "createAt"],
    foreignKeys = [
        ForeignKey(
            entity = RoomEntity::class,
            parentColumns = ["roomId"],
            childColumns = ["roomId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class FeedEntity(
    val roomId: Long,
    val userId: Long,
    val createAt: Date,
    val feedUrl: String?,
    val content: String?,
)
