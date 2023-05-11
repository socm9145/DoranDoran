package com.purple.data.rooms.model

import com.purple.core.database.entity.MemberEntity
import com.purple.core.database.entity.MemberRoomEntity
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class MembersResponse(
    val members: List<MemberResponse>,
    val roomId: Long,
)

@Serializable
data class MemberResponse(
    val name: String,
    val birth: @Contextual Date?,
    val profileUrl: String?,
    val userId: Long,
    val userRoomRole: String,
)

fun MemberResponse.asMemberRoomEntity(roomId: Long) = MemberRoomEntity(
    userId = userId,
    roomId = roomId,
    nickName = name,
    role = userRoomRole,
)

fun MemberResponse.asMemberEntity() = MemberEntity(
    birth = birth,
    userId = userId,
    profileUrl = profileUrl,
)
