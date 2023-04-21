package com.purple.hello.feature.rooms.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.hello.feature.rooms.fake.FakeFactory
import com.purple.hello.feature.rooms.intent.RoomsIntent
import com.purple.hello.feature.rooms.state.RoomsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class RoomsViewModel : ViewModel() {
    private val roomsIntent = Channel<RoomsIntent>()

    val roomsUiState: StateFlow<RoomsUiState> = roomsIntent.receiveAsFlow()
        .runningFold(RoomsUiState.Loading, ::reduceState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, RoomsUiState.Loading)

    private suspend fun reduceState(current: RoomsUiState, intent: RoomsIntent): RoomsUiState {
        return when (intent) {
            is RoomsIntent.AddRoom -> addRoom(intent.inputList)
            RoomsIntent.FetchRooms -> fetchRooms()
        }
    }

    fun sendIntent(intent: RoomsIntent) = viewModelScope.launch(Dispatchers.IO) {
        roomsIntent.send(intent)
    }

    private suspend fun addRoom(inputList: List<String>): RoomsUiState {
        return withContext(Dispatchers.IO) {
            val rooms = FakeFactory.makeRooms().first()
            fetchRooms()
        }
    }

    private suspend fun fetchRooms(): RoomsUiState {
        return withContext(Dispatchers.IO) {
            val rooms = FakeFactory.makeRooms().first()
            RoomsUiState.Loaded(rooms)
        }
    }
}
