package com.purple.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.purple.core.designsystem.theme.HiTheme

@Composable
fun SettingItem() {
    HiTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {

        }
    }
}

@Preview
@Composable
private fun PreviewSettingItem() {}
