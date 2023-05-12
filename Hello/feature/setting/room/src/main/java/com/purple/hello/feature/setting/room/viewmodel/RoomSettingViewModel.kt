package com.purple.hello.feature.setting.room.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.hello.feature.setting.room.navigation.roomIdArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomSettingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateRoomNameUseCase: com.purple.hello.domain.rooms.UpdateRoomNameUseCase,
    private val updateUserNameUseCase: com.purple.hello.domain.rooms.UpdateUserNameUseCase,
    private val updatePasswordUseCase: com.purple.hello.domain.rooms.UpdatePasswordUseCase,
    private val exitRoomUseCase: com.purple.hello.domain.rooms.ExitRoomUseCase,
    private val deleteRoomUseCase: com.purple.hello.domain.rooms.DeleteRoomUseCase,
    private val getRoomSettingsUseCase: com.purple.hello.domain.rooms.GetRoomSettingsUseCase,
) : ViewModel() {

    private val roomId: Long = checkNotNull(savedStateHandle[roomIdArg])

    //  TODO : Fake
    private val userRoomId = 0L
    val isHost = true

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

    fun getRoomSettingsInfo() =
        viewModelScope.launch(Dispatchers.IO) {
            getRoomSettingsUseCase(roomId = roomId)
        }
}
