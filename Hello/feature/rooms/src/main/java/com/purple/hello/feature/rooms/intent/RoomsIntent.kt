package com.purple.hello.feature.rooms.intent

sealed interface RoomsIntent {
    data class AddRoom(
        val inputList: List<String>,
    ) : RoomsIntent

    object FetchRooms : RoomsIntent
}
