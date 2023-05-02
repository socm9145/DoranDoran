package com.purple.hello.feature.setting.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                            // CHANGE_ROOM_NAME 항목을 클릭했을 때 동작 구현
                        }
                        SettingItemType.CHANGE_NAME -> {
                            // CHANGE_NAME 항목을 클릭했을 때 동작 구현
                        }
                        SettingItemType.CHANGE_PASSWORD -> {
                            // CHANGE_PASSWORD 항목을 클릭했을 때 동작 구현
                        }
                        SettingItemType.EXIT_GROUP -> {
                            // EXIT_GROUP 항목을 클릭했을 때 동작 구현
                        }
                        SettingItemType.DELETE_GROUP -> {
                            // DELETE_GROUP 항목을 클릭했을 때 동작 구현
                        }
                        else -> {}
                    }
                },
            )
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
        Column() {
            if (isHost) {
                hostSettingItems.forEach {
                    SettingItem(
                        onClick = { onClick(it) },
                        content = it,
                    )
                }
            } else {
                userSettingItems.forEach {
                    SettingItem(
                        onClick = { onClick(it) },
                        content = it,
                    )
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
        createInputDataByInputType(InputDialogType.QUESTION_PASSWORD, inputValue = ""),
        createInputDataByInputType(InputDialogType.EDIT_QUESTION_PASSWORD, inputValue = ""),
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
//    AppSettingScreen()
}
