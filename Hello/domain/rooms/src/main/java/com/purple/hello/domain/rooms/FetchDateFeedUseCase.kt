package com.purple.hello.domain.rooms

import android.os.Build
import androidx.annotation.RequiresApi
import com.purple.data.rooms.repository.FeedRepository
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class FetchDateFeedUseCase @Inject constructor(
    private val feedRepository: FeedRepository,
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(roomId: Long, date: LocalDateTime) {
        if(date.toLocalDate() == LocalDate.now()) {
            feedRepository.updateDateFeed(roomId, date)
        } else {
            // TODO: check roomDB
            // TODO: if exist -> none
            // TODO: else -> fetch
        }
    }
}
