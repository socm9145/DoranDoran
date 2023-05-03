package com.purple.hello.feature.setting.room.viewmodel

import androidx.lifecycle.ViewModel
import com.purple.hello.domain.setting.room.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomSettingViewModel @Inject constructor(
    private val changeRoomNameUseCase: ChangeRoomNameUseCase,
    private val changeUserNameUseCase: ChangeUserNameUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val exitRoomUseCase: ExitRoomUseCase,
    private val deleteRoomUseCase: DeleteRoomUseCase,
) : ViewModel() {

    // TODO: get from state...
    private val userRoomId = 0L
    private val roomId = 0

    fun changeRoomName(newRoomName: String) =
        changeRoomNameUseCase(userRoomId = userRoomId, newRoomName = newRoomName)

    fun changeUserName(newUserName: String) =
        changeUserNameUseCase(userRoomId = userRoomId, newUserName = newUserName)

    fun changeRoomPassword(newPasswordQuestion: String, newPassword: String) =
        changePasswordUseCase(roomId = roomId, newPasswordQuestion = newPasswordQuestion, newPassword = newPassword)

    fun exitRoom() =
        exitRoomUseCase(roomId = roomId)

    fun deleteRoom() =
        deleteRoomUseCase(roomId = roomId)
}
