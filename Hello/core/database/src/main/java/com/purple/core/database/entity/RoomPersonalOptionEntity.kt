package com.purple.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "room_common_option",
    primaryKeys = ["roomId"],
    foreignKeys = [
        ForeignKey(
            entity = RoomEntity::class,
            parentColumns = ["userRoomId"],
            childColumns = ["userRoomId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class RoomPersonalOptionEntity(
    val userRoomId: Long,
    val roomName: String,
    val nickName: String,
)
