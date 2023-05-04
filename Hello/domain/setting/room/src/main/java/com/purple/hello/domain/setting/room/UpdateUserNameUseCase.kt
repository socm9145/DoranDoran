package com.purple.hello.domain.setting.room

import com.purple.data.rooms.RoomRepository
import javax.inject.Inject

class UpdateUserNameUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    suspend operator fun invoke(userRoomId: Long, newUserName: String) =
        roomRepository.updateUserName(userRoomId, newUserName)
}
