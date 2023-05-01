package com.purple.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.designsystem.theme.HiTypography

@Composable
fun SettingItem(
    onClick: () -> Unit,
    isButtonIcon: Boolean,
    mainText: String,
    isWarning: Boolean,
    subText: String,
) {
    HiTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable(onClick = onClick)
                .background(Color.Unspecified)
                .padding(all = 16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically,
            ) {
                SettingItemContent(mainText, isWarning, subText)
                if (isButtonIcon) {
                    Icon(
                        imageVector = HiIcons.ArrowRight,
                        contentDescription = "더 보기",
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingItemContent(
    mainText: String,
    isWarning: Boolean,
    subText: String,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        Arrangement.SpaceBetween,
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Text(
                text = mainText,
                color = if (isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                style = HiTypography.headlineSmall,
            )
        }
        Box(contentAlignment = Alignment.CenterStart) {
            Text(
                text = subText,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = HiTypography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSettingItem() {
    HiTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SettingItem(
                onClick = {},
                mainText = "그룹 이름 변경",
                isWarning = false,
                subText = "나에게 보이는 그룹 이름을 변경할 수 있어요",
                isButtonIcon = true,
            )
        }
    }
}
