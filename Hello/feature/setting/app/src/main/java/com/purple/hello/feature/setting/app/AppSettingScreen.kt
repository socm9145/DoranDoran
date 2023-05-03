package com.purple.hello.feature.setting.app

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
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.component.SettingItem
import com.purple.core.designsystem.dialog.HiAlertDialog
import com.purple.core.designsystem.dialog.HiInputDialog
import com.purple.core.designsystem.dialog.InputData
import com.purple.core.designsystem.dialog.createInputDataByInputType
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.model.DeleteDialogType
import com.purple.core.model.InputDialogType
import com.purple.core.model.SettingItemType

@Composable
fun AppSettingRoute(
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
            AppSettingAppBar()
            AppSettingScreen(
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
                    onConfirm = { /* TODO */ },
                )
            }
            shouldShowChangeUserNameDialog -> {
                ChangeUserNameDialog(
                    onDismiss = {
                        shouldShowChangeUserNameDialog = false
                    },
                    onConfirm = { /* TODO */ },
                )
            }
            shouldShowChangePasswordDialog -> {
                ChangePasswordDialog(
                    onDismiss = {
                        shouldShowChangePasswordDialog = false
                    },
                    onConfirm = { /* TODO */ },
                )
            }
            shouldShowExitRoomDialog -> {
                ExitRoomDialog(
                    onDismiss = {
                        shouldShowExitRoomDialog = false
                    },
                    onDelete = { /* TODO */ },
                )
            }
            shouldShowDeleteRoomDialog -> {
                DeleteRoomDialog(
                    onDismiss = {
                        shouldShowDeleteRoomDialog = false
                    },
                    onDelete = { /* TODO */ },
                )
            }
        }
    }
}

@Composable
private fun AppSettingScreen(
    isHost: Boolean,
    onClick: (SettingItemType) -> Unit,
) {
    val userSettingItems = listOf(
        SettingItemType.CHANGE_ROOM_NAME,
        SettingItemType.CHANGE_NAME,
        SettingItemType.EXIT_GROUP,
    )
    val hostSettingItems = listOf(
        SettingItemType.CHANGE_ROOM_NAME,
        SettingItemType.CHANGE_NAME,
        SettingItemType.CHANGE_PASSWORD,
        SettingItemType.EXIT_GROUP,
        SettingItemType.DELETE_GROUP,
    )
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column {
            if (isHost) {
                hostSettingItems.forEachIndexed { index, it ->
                    SettingItem(
                        onClick = { onClick(it) },
                        content = it,
                    )
                    if (index < hostSettingItems.lastIndex) {
                        Divider(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                            thickness = 1.dp,
                        )
                    }
                }
            } else {
                userSettingItems.forEachIndexed { index, it ->
                    SettingItem(
                        onClick = { onClick(it) },
                        content = it,
                    )
                    if (index < userSettingItems.lastIndex) {
                        Divider(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                            thickness = 1.dp,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChangeRoomNameDialog(
    onDismiss: () -> Unit,
    onConfirm: (List<InputData>) -> Unit,
) {
    /* TODO : inputValue 에 현재 RoomName 넣기 */
    val inputList = listOf(
        createInputDataByInputType(InputDialogType.EDIT_ROOM_NAME, inputValue = ""),
    )
    HiInputDialog(
        questionContent = inputList,
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(inputList) },
        confirmButtonText = "변경하기",
        dismissButtonText = "취소",
    )
}

@Composable
private fun ChangeUserNameDialog(
    onDismiss: () -> Unit,
    onConfirm: (List<InputData>) -> Unit,
) {
    /* TODO : inputValue 에 현재 UserName 넣기 */
    val inputList = listOf(
        createInputDataByInputType(InputDialogType.EDIT_NAME, inputValue = ""),
    )
    HiInputDialog(
        questionContent = inputList,
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(inputList) },
        confirmButtonText = "변경하기",
        dismissButtonText = "취소",
    )
}

@Composable
private fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (List<InputData>) -> Unit,
) {
    /* TODO : QUESTION_PASSWORD -> inputValue 에 현재 password 질문 넣기 */
    val inputList = listOf(
        createInputDataByInputType(InputDialogType.EDIT_QUESTION_PASSWORD, inputValue = ""),
        createInputDataByInputType(InputDialogType.EDIT_PASSWORD, inputValue = ""),
    )
    HiInputDialog(
        questionContent = inputList,
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(inputList) },
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
private fun AppSettingAppBar() {
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
    AppSettingRoute(isHost = true)
}
