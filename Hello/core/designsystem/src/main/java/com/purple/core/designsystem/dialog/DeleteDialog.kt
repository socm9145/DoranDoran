package com.purple.core.designsystem.dialog

import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.purple.core.designsystem.component.HiOutlinedButton
import com.purple.core.designsystem.theme.HiTheme

enum class DeleteType(val questionText: String) {
    EXIT_GROUP("정말 방에서 나가시나요?"),
    DELETE_GROUP("이 방의 모든 것을 지우실 건가요?"),
    LOGOUT("로그아웃 하기"),
    DELETE_USER("회원 탈퇴 하기"),
}

data class QuestionData(
    val questionText: String,
)

fun createQuestionType(type: DeleteType): QuestionData {
    return QuestionData(
        questionText = type.questionText,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    content: QuestionData,
) {
    HiTheme() {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier
                .widthIn(max = 300.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = content.questionText,
                    style = MaterialTheme.typography.headlineMedium,
                )
            },
            confirmButton = {
                HiOutlinedButton(
                    onClick = onDelete,
                    text = {
                        Text(
                            text = "네",
                            color = MaterialTheme.colorScheme.error,
                        )
                    },
                )
            },
            dismissButton = {
                HiOutlinedButton(
                    onClick = onDismiss,
                    text = {
                        Text(
                            text = "아니요",
                            color = MaterialTheme.colorScheme.outline,
                        )
                    },
                )
            },
        )
    }
}

@Preview
@Composable
private fun PreviewDeleteDialog() {
    DeleteDialog(
        onDismiss = {},
        onDelete = {},
        createQuestionType(DeleteType.EXIT_GROUP),
    )
}
