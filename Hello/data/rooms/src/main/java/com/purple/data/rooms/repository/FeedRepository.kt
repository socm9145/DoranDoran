package com.purple.data.rooms.repository

import com.purple.core.model.Feed
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.time.LocalDateTime

interface FeedRepository {

    fun getQuestion(roomId: Long, date: LocalDateTime): Flow<String>

    fun getDateFeed(roomId: Long, date: LocalDateTime): Flow<List<Feed>>

    suspend fun updateQuestion(roomId: Long, date: LocalDateTime): String

    suspend fun uploadFeed(roomId: Long, feedImage: File)
}
