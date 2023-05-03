package com.purple.hello.domain.setting.room

import com.purple.data.rooms.RoomRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    operator fun invoke(roomId: Int, newPasswordQuestion: String, newPassword: String) =
        roomRepository.updatePassword(roomId, newPasswordQuestion, newPassword)
}
