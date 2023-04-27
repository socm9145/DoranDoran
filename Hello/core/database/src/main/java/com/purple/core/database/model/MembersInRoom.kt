package com.purple.core.database.model // ktlint-disable filename

import androidx.room.Embedded
import androidx.room.Relation
import com.purple.core.database.entity.MemberEntity
import com.purple.core.database.entity.MemberRoomEntity

data class MemberInRoom(
    @Embedded val memberRoom: MemberRoomEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId",
    )
    val member: MemberEntity
)

