package com.purple.hello.domain.rooms

import com.purple.data.rooms.RoomRepository
import javax.inject.Inject

class CreateRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    suspend operator fun invoke(roomName: String, userName: String, roomQuestion: String, roomPassword: String) =
        roomRepository.createRoom(roomName, userName, roomQuestion, roomPassword)
}
