package com.purple.data.rooms.model

import com.purple.core.database.entity.MemberEntity
import com.purple.core.database.entity.MemberRoomEntity
import com.purple.hello.core.network.utils.birthToDate
import kotlinx.serialization.Serializable

@Serializable
data class MembersResponse(
    val members: List<MemberResponse>,
    val roomId: Long,
)

@Serializable
data class MemberResponse(
    val name: String,
    val birth: String?,
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
    birth = birth?.birthToDate(),
    userId = userId,
    profileUrl = profileUrl,
)
