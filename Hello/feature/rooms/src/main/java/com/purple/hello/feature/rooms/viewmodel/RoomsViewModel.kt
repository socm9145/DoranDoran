package com.purple.hello.feature.rooms.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.core.model.*
import com.purple.core.model.type.InputDialogType
import com.purple.hello.domain.rooms.*
import com.purple.hello.feature.rooms.state.RoomsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    getRoomListFlow: GetRoomListUseCase,
    private val joinRoomUseCase: JoinRoomUseCase,
    private val getJoinInfoUseCase: GetJoinInfoUseCase,
    private val fetchRoomsUseCase: FetchRoomsUseCase,
    private val createRoomUseCase: CreateRoomUseCase,
) : ViewModel() {

    private val rooms: Flow<Result<List<Room>>> = getRoomListFlow().asResult()

    val roomsUiState: StateFlow<RoomsUiState> =
        rooms.map {
            when (it) {
                is Result.Success -> { RoomsUiState.Success(it.data) }
                is Result.Loading -> { RoomsUiState.Loading }
                is Result.Error -> { RoomsUiState.Error(it.exception) }
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

    suspend fun joinRoom(roomId: Long, joinRoomData: JoinRoomInputValue) = joinRoomUseCase(roomId, joinRoomData)

    suspend fun fetchRoom() = fetchRoomsUseCase()

    suspend fun getJoinData(joinRoomId: Long) = getJoinInfoUseCase(joinRoomId)
}
