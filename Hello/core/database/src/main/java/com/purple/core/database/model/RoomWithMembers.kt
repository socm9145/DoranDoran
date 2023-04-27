package com.purple.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.purple.core.database.entity.MemberRoomEntity
import com.purple.core.database.entity.RoomEntity

data class RoomWithMembers(
    @Embedded val roomEntity: RoomEntity,
    @Relation(
        parentColumn = "roomId",
        entityColumn = "roomId",
        entity = MemberRoomEntity::class,
    )
    val members: List<MemberInRoom>
)
