package com.purple.core.database.entity

import androidx.room.Entity

@Entity(
    tableName = "room",
    primaryKeys = ["roomId", "userRoomId"]
)
data class RoomEntity(
    val roomId: Long,
    val userRoomId: Long,
    val recentVisitedTime: Long
)
