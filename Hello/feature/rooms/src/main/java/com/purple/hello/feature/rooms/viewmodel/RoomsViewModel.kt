package com.purple.hello.feature.rooms.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.core.model.Result
import com.purple.core.model.Room
import com.purple.core.model.asResult
import com.purple.hello.domain.rooms.CreateRoomUseCase
import com.purple.hello.domain.rooms.FetchRoomsUseCase
import com.purple.hello.domain.rooms.GetRoomListUseCase
import com.purple.hello.feature.rooms.state.RoomsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    getRoomListFlow: GetRoomListUseCase,
    private val fetchRoomsUseCase: FetchRoomsUseCase,
    private val createRoomUseCase: CreateRoomUseCase,
) : ViewModel() {

    private val rooms: Flow<Result<List<Room>>> = getRoomListFlow().asResult()

    val roomsUiState: StateFlow<RoomsUiState> =
        rooms.map {
            when (it) {
                is Result.Success -> {
                    Log.d("roomsUiState", "Success")
                    RoomsUiState.Success(it.data)
                }
                is Result.Loading -> {
                    Log.d("roomsUiState", "Loading")
                    RoomsUiState.Loading
                }
                is Result.Error -> {
                    Log.d("roomsUiState", it.exception.toString())
                    RoomsUiState.Error(it.exception)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RoomsUiState.Loading,
        )

    suspend fun createRoom(
        roomName: String,
        userName: String,
        question: String,
        password: String,
        onRoomCreate: (Long) -> Unit,
    ) {
        val roomId = createRoomUseCase(roomName, userName, question, password)
        onRoomCreate(roomId)
    }

    suspend fun fetchRoom() = fetchRoomsUseCase()
}
