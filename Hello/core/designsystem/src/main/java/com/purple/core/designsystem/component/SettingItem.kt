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

enum class TextType(val mainText: String, val subText: String, val isWarning: Boolean, val isButtonIcon: Boolean) {
    CHANGE_ROOM_NAME("그룹 이름 변경", "나에게 보이는 그룹 이름을 변경할 수 있어요", false, true),
    CHANGE_NAME("나의 이름 변경", "그룹에서 사용할 나의 이름을 변경할 수 있어요", false, true),
    CHANGE_PASSWORD("그룹 비밀번호 변경", "그룹 관리자에게만 가능한 기능이에요", false, true),
    EXIT_GROUP("그룹에서 나가기", "나가면 다시 초대를 받아야 들어올 수 있어요", true, false),
    DELETE_GROUP("그룹 삭제", "그룹을 삭제하면 아무것도 남지않아요....ㅠ", true, false),
    POLICY("개인 정보 처리 방침", "", false, true),
    APP_INFO("시스템(앱) 정보", "", false, true),
    LOGOUT("로그아웃", "다시 로그인하면 모든 정보를 다시 보실 수 있어요", true, false),
    DELETE_USER("회원 탈퇴", "탈퇴하시면 지금까지의 모든 정보가 사라져요!", true, false),
}

data class TextData(
    val mainText: String,
    val subText: String,
    val isWarning: Boolean,
    val isButtonIcon: Boolean,
)

fun createTextType(type: TextType): TextData {
    return TextData(
        mainText = type.mainText,
        subText = type.subText,
        isWarning = type.isWarning,
        isButtonIcon = type.isButtonIcon,
    )
}

@Composable
fun SettingItem(
    onClick: () -> Unit,
    content: TextData,
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
                SettingItemContent(content)
                if (content.isButtonIcon) {
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
    content: TextData,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        Arrangement.SpaceBetween,
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Text(
                text = content.mainText,
                color = if (content.isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                style = HiTypography.headlineSmall,
            )
        }
        Box(contentAlignment = Alignment.CenterStart) {
            Text(
                text = content.subText,
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
                createTextType(TextType.CHANGE_ROOM_NAME),
            )
        }
    }
}
