package com.purple.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.purple.core.database.entity.FeedEntity
import com.purple.core.database.entity.MemberRoomEntity
import com.purple.core.model.Feed

data class FeedWithAuthor(
    @Embedded val feed: FeedEntity,
    @Relation(
        parentColumn = "roomId",
        entityColumn = "roomId",
        entity = MemberRoomEntity::class,
    )
    val author: MemberInRoom
)

fun FeedWithAuthor.asExternalModel() = Feed(
    headerImageUrl = feed.feedUrl,
    content = feed.content,
    author = author.asExternalModel()
)
