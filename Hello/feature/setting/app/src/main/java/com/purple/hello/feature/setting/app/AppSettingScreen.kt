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
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.model.DeleteDialogType
import com.purple.core.model.SettingItemType
import com.purple.core.model.SettingItemType.Companion.getItemsForApp

@Composable
fun AppSettingRoute() {
    var shouldShowLogoutDialog by remember { mutableStateOf(false) }
    var shouldShowSecessionDialog by remember { mutableStateOf(false) }

    HiTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
        ) {
            AppSettingAppBar()
            AppSettingScreen(
                onClick = { itemType ->
                    when (itemType) {
                        SettingItemType.POLICY -> {
//                            TODO : 개인 정보 처리 방침 URL 이동
                        }
                        SettingItemType.APP_INFO -> {
//                            TODO : 구글 스토어 내부 설명 페이지 URL 이동
                        }
                        SettingItemType.LOGOUT -> {
                            shouldShowLogoutDialog = true
                        }
                        SettingItemType.DELETE_USER -> {
                            shouldShowSecessionDialog = true
                        }
                        else -> {}
                    }
                },
            )
        }
        when {
            shouldShowLogoutDialog -> {
                LogoutDialog(
                    onDismiss = {
                        shouldShowLogoutDialog = false
                    },
                    onDelete = { TODO() },
                )
            }
            shouldShowSecessionDialog -> {
                SecessionDialog(
                    onDismiss = {
                        shouldShowSecessionDialog = false
                    },
                    onDelete = { TODO() },
                )
            }
        }
    }
}

@Composable
private fun AppSettingScreen(
    onClick: (SettingItemType) -> Unit,
) {
    val settingItems = getItemsForApp()

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
private fun LogoutDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
) {
    HiAlertDialog(
        onDismiss = onDismiss,
        onDelete = onDelete,
        content = DeleteDialogType.LOGOUT,
    )
}

@Composable
private fun SecessionDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
) {
    HiAlertDialog(
        onDismiss = onDismiss,
        onDelete = onDelete,
        content = DeleteDialogType.DELETE_USER,
    )
}

@Composable
private fun AppSettingAppBar() {
    HiTopAppBar(
        title = "앱 설정",
        navigationIcon = HiIcons.ArrowBack,
        navigationIconContentDescription = "뒤로 가기",
        actions = {},
        onNavigationClick = {},
    )
}

@Preview
@Composable
private fun PreviewAppSettingScreen() {
    AppSettingRoute()
}
