package com.purple.hello.feature.rooms.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.core.model.Result
import com.purple.core.model.Room
import com.purple.core.model.asResult
import com.purple.hello.domain.rooms.FetchRoomDetailUseCase
import com.purple.hello.domain.rooms.GetQuestionUseCase
import com.purple.hello.domain.rooms.GetRoomCodeUseCase
import com.purple.hello.domain.rooms.GetSelectedRoomUseCase
import com.purple.hello.feature.rooms.navigation.roomIdArg
import com.purple.hello.feature.rooms.state.FeedUiState
import com.purple.hello.feature.rooms.state.RoomDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RoomDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    getRoomFlow: GetSelectedRoomUseCase,
    getRoomCode: GetRoomCodeUseCase,
    getQuestionFlow: GetQuestionUseCase,
    fetchRoomDetail: FetchRoomDetailUseCase,
) : ViewModel() {

    private var selectedRoomId: Long = checkNotNull(savedStateHandle[roomIdArg])
    private val selectedDate = MutableSharedFlow<Date>()
    val roomCode: MutableStateFlow<String> = MutableStateFlow("")

    private var selectedRoom: Flow<Result<Room>> = getRoomFlow(selectedRoomId)
        .onStart {
            withContext(Dispatchers.IO) {
                fetchRoomDetail(selectedRoomId)
                roomCode.value = getRoomCode(selectedRoomId)
            }
        }
        .asResult()
    private val dateQuestion: Flow<Result<String>> = selectedDate.flatMapLatest {
        getQuestionFlow(roomId = selectedRoomId, date = it).asResult()
    }

    val roomDetailUiState: StateFlow<RoomDetailUiState> =
        selectedRoom.map {
            when (it) {
                is Result.Loading -> RoomDetailUiState.Loading
                is Result.Success -> RoomDetailUiState.Success(it.data)
                is Result.Error -> RoomDetailUiState.Error(it.exception)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RoomDetailUiState.Loading,
        )

    val feedUiState: StateFlow<FeedUiState> =
        dateQuestion.map {
            when (it) {
                is Result.Loading -> FeedUiState.Loading
                is Result.Success -> FeedUiState.Success(
                    date = Date(),
                    feeds = emptyList(),
                    question = it.data,
                )
                is Result.Error -> FeedUiState.Error(it.exception)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FeedUiState.Loading,
        )
}
