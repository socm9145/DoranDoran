package com.purple.hello.feature.setting.room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.component.SettingItem
import com.purple.core.designsystem.dialog.HiAlertDialog
import com.purple.core.designsystem.dialog.HiInputDialog
import com.purple.core.designsystem.dialog.createInputDataByInputType
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.model.DeleteDialogType
import com.purple.core.model.InputDialogType
import com.purple.core.model.SettingItemType
import com.purple.core.model.SettingItemType.Companion.getItemsForHost
import com.purple.core.model.SettingItemType.Companion.getItemsForUser
import com.purple.hello.feature.setting.room.viewmodel.RoomSettingViewModel

@Composable
fun RoomSettingRoute(
    roomSettingViewModel: RoomSettingViewModel = hiltViewModel(),
    isHost: Boolean,
) {
    var shouldShowChangeRoomNameDialog by remember { mutableStateOf(false) }
    var shouldShowChangeUserNameDialog by remember { mutableStateOf(false) }
    var shouldShowChangePasswordDialog by remember { mutableStateOf(false) }
    var shouldShowExitRoomDialog by remember { mutableStateOf(false) }
    var shouldShowDeleteRoomDialog by remember { mutableStateOf(false) }

    HiTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
        ) {
            RoomSettingAppBar()
            RoomSettingScreen(
                isHost = isHost,
                onClick = { itemType ->
                    when (itemType) {
                        SettingItemType.CHANGE_ROOM_NAME -> {
                            shouldShowChangeRoomNameDialog = true
                        }
                        SettingItemType.CHANGE_NAME -> {
                            shouldShowChangeUserNameDialog = true
                        }
                        SettingItemType.CHANGE_PASSWORD -> {
                            shouldShowChangePasswordDialog = true
                        }
                        SettingItemType.EXIT_GROUP -> {
                            shouldShowExitRoomDialog = true
                        }
                        SettingItemType.DELETE_GROUP -> {
                            shouldShowDeleteRoomDialog = true
                        }
                        else -> {}
                    }
                },
            )
        }
        when {
            shouldShowChangeRoomNameDialog -> {
                ChangeRoomNameDialog(
                    onDismiss = {
                        shouldShowChangeRoomNameDialog = false
                    },
                    onConfirm = roomSettingViewModel::changeRoomName,
                )
            }
            shouldShowChangeUserNameDialog -> {
                ChangeUserNameDialog(
                    onDismiss = {
                        shouldShowChangeUserNameDialog = false
                    },
                    onConfirm = roomSettingViewModel::changeUserName,
                )
            }
            shouldShowChangePasswordDialog -> {
                ChangePasswordDialog(
                    onDismiss = {
                        shouldShowChangePasswordDialog = false
                    },
                    onConfirm = roomSettingViewModel::changeRoomPassword,
                )
            }
            shouldShowExitRoomDialog -> {
                ExitRoomDialog(
                    onDismiss = {
                        shouldShowExitRoomDialog = false
                    },
                    onDelete = roomSettingViewModel::exitRoom,
                )
            }
            shouldShowDeleteRoomDialog -> {
                DeleteRoomDialog(
                    onDismiss = {
                        shouldShowDeleteRoomDialog = false
                    },
                    onDelete = roomSettingViewModel::deleteRoom,
                )
            }
        }
    }
}

@Composable
private fun RoomSettingScreen(
    isHost: Boolean,
    onClick: (SettingItemType) -> Unit,
) {
    val settingItems = if (isHost) getItemsForHost() else getItemsForUser()

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column {
            settingItems.forEachIndexed { index, it ->
                SettingItem(
                    onClick = { onClick(it) },
                    content = it,
                )
                if (index < settingItems.lastIndex) {
                    Divider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                        thickness = 1.dp,
                    )
                }
            }
        }
    }
}

@Composable
private fun ChangeRoomNameDialog(
    onDismiss: () -> Unit,
    onConfirm: (newRoomName: String) -> Unit,
) {
    /* TODO : inputValue 에 현재 RoomName 넣기 */
    val newRoomName = createInputDataByInputType(InputDialogType.EDIT_ROOM_NAME, inputValue = "")

    HiInputDialog(
        questionContent = listOf(newRoomName),
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(newRoomName.inputValue) },
        confirmButtonText = "변경하기",
        dismissButtonText = "취소",
    )
}

@Composable
private fun ChangeUserNameDialog(
    onDismiss: () -> Unit,
    onConfirm: (newUserName: String) -> Unit,
) {
    /* TODO : inputValue 에 현재 UserName 넣기 */
    val newUserName = createInputDataByInputType(InputDialogType.EDIT_NAME, inputValue = "")

    HiInputDialog(
        questionContent = listOf(newUserName),
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(newUserName.inputValue) },
        confirmButtonText = "변경하기",
        dismissButtonText = "취소",
    )
}

@Composable
private fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (newPasswordQuestion: String, newPassword: String) -> Unit,
) {
    /* TODO : QUESTION_PASSWORD -> inputValue 에 현재 password 질문 넣기 */
    val newPasswordQuestion = createInputDataByInputType(InputDialogType.EDIT_QUESTION_PASSWORD, inputValue = "")
    val newPassword = createInputDataByInputType(InputDialogType.EDIT_PASSWORD, inputValue = "")
    HiInputDialog(
        questionContent = listOf(newPasswordQuestion, newPassword),
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(newPasswordQuestion.inputValue, newPassword.inputValue) },
        confirmButtonText = "변경하기",
        dismissButtonText = "취소",
    )
}

@Composable
private fun ExitRoomDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
) {
    HiAlertDialog(
        onDismiss = onDismiss,
        onDelete = onDelete,
        content = DeleteDialogType.EXIT_GROUP,
    )
}

@Composable
private fun DeleteRoomDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
) {
    HiAlertDialog(
        onDismiss = onDismiss,
        onDelete = onDelete,
        content = DeleteDialogType.DELETE_GROUP,
    )
}

@Composable
private fun RoomSettingAppBar() {
    HiTopAppBar(
        title = "설정",
        navigationIcon = HiIcons.ArrowBack,
        navigationIconContentDescription = "뒤로 가기",
        actions = {},
        onNavigationClick = { /* TODO : 뒤로가기 */ },
    )
}

@Preview
@Composable
private fun PreviewAppSettingScreen() {
    RoomSettingRoute(isHost = true)
}
