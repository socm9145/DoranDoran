package com.purple.hello.feature.rooms.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.core.model.Result
import com.purple.core.model.Room
import com.purple.core.model.asResult
import com.purple.hello.domain.rooms.GetSelectedRoomUseCase
import com.purple.hello.feature.rooms.navigation.roomIdArg
import com.purple.hello.feature.rooms.state.RoomDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RoomDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getRoomFlow: GetSelectedRoomUseCase,
) : ViewModel() {

    private var selectedRoomId: Long = checkNotNull(savedStateHandle[roomIdArg])

    private var selectedRoom: Flow<Result<Room>> = getRoomFlow(selectedRoomId).asResult()

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
}
