package com.purple.data.rooms.model

import com.purple.core.database.entity.MemberEntity
import com.purple.core.database.entity.MemberRoomEntity
import kotlinx.serialization.Serializable

@Serializable
data class MembersResponse(
    val members: List<MemberResponse>,
    val roomId: Long,
)

@Serializable
data class MemberResponse(
    val name: String,
    val profileUrl: String?,
    val userId: Long,
)

fun MemberResponse.asMemberRoomEntity(roomId: Long) = MemberRoomEntity(
    userId = userId,
    roomId = roomId,
    nickName = name,
)

fun MemberResponse.asMemberEntity() = MemberEntity(
    userId = userId,
    profileUrl = profileUrl,
)
