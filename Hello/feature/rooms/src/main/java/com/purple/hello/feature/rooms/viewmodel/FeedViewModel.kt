package com.purple.hello.feature.rooms.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.core.model.Feed
import com.purple.core.model.Result
import com.purple.core.model.asResult
import com.purple.hello.domain.rooms.*
import com.purple.hello.domain.rooms.feed.FetchDateFeedUseCase
import com.purple.hello.domain.rooms.feed.GetDateFeedUseCase
import com.purple.hello.domain.rooms.feed.GetQuestionUseCase
import com.purple.hello.feature.rooms.navigation.roomIdArg
import com.purple.hello.feature.rooms.state.FeedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class FeedViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getQuestionFlow: GetQuestionUseCase,
    getFeedFlow: GetDateFeedUseCase,
    private val fetchDateFeed: FetchDateFeedUseCase,
) : ViewModel() {

    private val userId = checkNotNull(savedStateHandle["userId"])
    private var selectedRoomId: Long = checkNotNull(savedStateHandle[roomIdArg])
    private val selectedDate = MutableSharedFlow<LocalDateTime>()

    private val dateQuestion: Flow<Result<String>> = selectedDate.flatMapLatest {
        getQuestionFlow(roomId = selectedRoomId, date = it).asResult()
    }
    private val dateFeedList: Flow<Result<List<Feed>>> = selectedDate.flatMapLatest {
        getFeedFlow(roomId = selectedRoomId, date = it).asResult()
    }

    val feedUiState: StateFlow<FeedUiState> =
        dateFeedList.map {
            when (it) {
                is Result.Loading -> FeedUiState.Loading
                is Result.Success -> {
                    FeedUiState.Success(
                        feeds = it.data,
                        isPossibleToUpload = it.data.none { feed ->
                            feed.author.id == userId
                        },
                    )
                }
                is Result.Error -> FeedUiState.Error(it.exception)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FeedUiState.Loading,
        )

    fun selectDate(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            selectedDate.emit(date)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchFeed(roomId: Long, date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchDateFeed(
                date = date,
                roomId = roomId,
            )
        }
    }
}
