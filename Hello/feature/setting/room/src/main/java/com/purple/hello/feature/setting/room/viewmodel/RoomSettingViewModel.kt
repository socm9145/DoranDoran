package com.purple.hello.feature.setting.room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.hello.domain.setting.room.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomSettingViewModel @Inject constructor(
    private val updateRoomNameUseCase: UpdateRoomNameUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val exitRoomUseCase: ExitRoomUseCase,
    private val deleteRoomUseCase: DeleteRoomUseCase,
) : ViewModel() {

    // TODO: get from state...
    private val userRoomId = 0L
    private val roomId = 0L

    fun updateRoomName(newRoomName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            updateRoomNameUseCase(userRoomId = userRoomId, newRoomName = newRoomName)
        }

    fun updateUserName(newUserName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            updateUserNameUseCase(userRoomId = userRoomId, newUserName = newUserName)
        }

    fun updateRoomPassword(newPasswordQuestion: String, newPassword: String) =
        viewModelScope.launch(Dispatchers.IO) {
            updatePasswordUseCase(roomId = roomId, newPasswordQuestion = newPasswordQuestion, newPassword = newPassword)
        }

    fun exitRoom() =
        viewModelScope.launch(Dispatchers.IO) {
            exitRoomUseCase(userRoomId = userRoomId)
        }

    fun deleteRoom() =
        viewModelScope.launch(Dispatchers.IO) {
            deleteRoomUseCase(roomId = roomId)
        }
}
