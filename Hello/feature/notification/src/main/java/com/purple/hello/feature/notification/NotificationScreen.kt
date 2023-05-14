package com.purple.hello.feature.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.component.NotificationItem
import com.purple.core.designsystem.component.SettingItem
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.model.Notification
import com.purple.hello.feature.notification.viewmodel.NotificationViewModel

@Composable
fun NotificationRoute(
    notificationViewModel: NotificationViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val notificationList by notificationViewModel.notifications.collectAsState()

    HiTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
        ) {
            NotificationAppBar(onBackClick = onBackClick)
            NotificationScreen(notificationList = notificationList)
        }
    }
}

@Composable
private fun NotificationScreen(
    notificationList: List<Notification>,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column {
            notificationList.forEachIndexed { index, it ->
                NotificationItem(
                    titleContent = it.title,
                    bodyContent = it.body,
                    timeContent = it.timestamp.toString(),
                )
                if (index < notificationList.lastIndex) {
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
private fun NotificationAppBar(
    onBackClick: () -> Unit,
) {
    HiTopAppBar(
        title = "알림 목록",
        navigationIcon = HiIcons.ArrowBack,
        navigationIconContentDescription = "뒤로 가기",
        actions = {},
        onNavigationClick = { onBackClick() },
    )
}

@Preview
@Composable
private fun PreviewNotificationScreen() {
    NotificationRoute(onBackClick = {})
}
