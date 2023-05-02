package com.purple.hello.feature.setting.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme

@Composable
fun AppSettingScreen() {
    HiTheme {
        HiTopAppBar(
            title = "설정",
            navigationIcon = HiIcons.ArrowBack,
            navigationIconContentDescription = "뒤로 가기",
            actions = {},
            onNavigationClick = { /* TODO : 뒤로가기 */ },
        )
    }
}

@Preview
@Composable
private fun PreviewAppSettingScreen() {
    AppSettingScreen()
}
