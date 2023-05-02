package com.purple.hello.feature.rooms.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.core.designsystem.dialog.InputData
import com.purple.core.model.*
import com.purple.hello.domain.rooms.GetRoomListUseCase
import com.purple.hello.feature.rooms.state.RoomsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    getRoomListFlow: GetRoomListUseCase,
) : ViewModel() {

    private val rooms: Flow<Result<List<Room>>> = getRoomListFlow().asResult()

    val roomsUiState: StateFlow<RoomsUiState> =
        rooms.map {
            when (it) {
                is Result.Success -> RoomsUiState.Success(it.data)
                is Result.Loading -> RoomsUiState.Loading
                is Result.Error -> RoomsUiState.Error(it.exception)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RoomsUiState.Loading,
        )

    fun createRoom(inputList: List<InputData>) {
        /*TODO*/
    }
}
