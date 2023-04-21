package com.purple.hello.feature.rooms.state

import com.purple.hello.domain.feature.model.Room

sealed interface RoomsUiState {
    object Loading : RoomsUiState

    data class Loaded(
        val rooms: List<Room>,
    ) : RoomsUiState

    data class Error(val message: String?) : RoomsUiState
}
