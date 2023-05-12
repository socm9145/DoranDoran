package com.purple.hello.feature.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme

@Composable
fun AlarmRoute(
    onBackClick: () -> Unit,
) {
    HiTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
        ) {
            AlarmAppBar(onBackClick = onBackClick)
            AlarmScreen()
        }
    }
}

@Composable
private fun AlarmScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column {
            // TODO : workmanager 로 받아온 아이템 보여주기
        }
    }
}

@Composable
private fun AlarmAppBar(
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
private fun PreviewAlarmScreen() {
    AlarmRoute(onBackClick = {})
}
