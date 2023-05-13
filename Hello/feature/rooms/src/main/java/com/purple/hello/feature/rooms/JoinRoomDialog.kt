package com.purple.hello.feature.rooms

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.purple.core.designsystem.dialog.HiInputDialog
import com.purple.core.model.InputData
import com.purple.core.model.JoinRoomInputValue
import com.purple.hello.feature.rooms.viewmodel.RoomsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
internal fun JoinRoomDialog(
    roomsViewModel: RoomsViewModel = hiltViewModel(),
    joinRoomId: Long,
    onDismiss: () -> Unit
) {
    var joinData by remember { mutableStateOf<JoinRoomInputValue?>(null) }

    LaunchedEffect(joinRoomId) {
        launch(Dispatchers.IO) {
            joinData = roomsViewModel.getJoinData(joinRoomId)
        }
    }

    if(joinData != null) {
        HiInputDialog(
            questionContent = listOf(
                joinData!!.roomName,
                joinData!!.nickName,
                joinData!!.password,
            ),
            onDismiss = onDismiss,
            onConfirm = {
                Log.d("roomName", joinData!!.roomName.inputValue)
                Log.d("nickName", joinData!!.nickName.inputValue)
                Log.d("password", joinData!!.password.inputValue)
            },
            confirmButtonText = "confirm",
            dismissButtonText = "dismiss"
        )
    }
}
