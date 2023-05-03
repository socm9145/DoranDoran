package com.purple.hello.domain.setting.room

import com.purple.data.rooms.RoomRepository
import javax.inject.Inject

class DeleteRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    operator fun invoke(roomId: Int) =
        roomRepository.deleteRoom(roomId)
}
