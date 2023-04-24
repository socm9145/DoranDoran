package com.purple.hello.feature.rooms.state

import com.purple.hello.domain.feature.model.Room

sealed interface RoomsUiState {
    object Loading : RoomsUiState

    data class Success(
        val rooms: List<Room>,
    ) : RoomsUiState

    data class Error(val message: Throwable?) : RoomsUiState
}
