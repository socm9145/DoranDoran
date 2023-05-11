package com.purple.data.rooms.model

import android.annotation.SuppressLint
import com.purple.core.database.entity.FeedEntity
import kotlinx.serialization.*
import java.text.SimpleDateFormat
import java.util.*

@Serializable
data class CreateFeedResponse(
    @SerialName("content") val content: String?,
    @SerialName("createAt") val createdAt: String,
    @SerialName("feedType") val feedType: String?,
    @SerialName("feedUrl") val feedUrl: String?,
    @SerialName("feedId") val feedId: Long,
    @SerialName("userRoomId") val userRoomId: Long,
)

fun CreateFeedResponse.asFeedEntity(roomId: Long, userId: Long) = FeedEntity(
    roomId = roomId,
    userId = userId,
    createAt = createdAt.toDate() as Date,
    feedUrl = feedUrl,
    content = content,
)

@SuppressLint("SimpleDateFormat")
fun String.toDate() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(this)

