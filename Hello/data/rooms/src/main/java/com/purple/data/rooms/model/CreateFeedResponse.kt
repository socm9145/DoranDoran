package com.purple.data.rooms.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.purple.core.database.entity.FeedEntity
import com.purple.hello.core.network.utils.toLocalDateTime
import kotlinx.serialization.*
import java.time.LocalDateTime
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

@RequiresApi(Build.VERSION_CODES.O)
fun CreateFeedResponse.asFeedEntity(roomId: Long, userId: Long) = FeedEntity(
    roomId = roomId,
    userId = userId,
    createAt = createdAt.toLocalDateTime() ?: LocalDateTime.now(),
    feedUrl = feedUrl,
    content = content,
)
