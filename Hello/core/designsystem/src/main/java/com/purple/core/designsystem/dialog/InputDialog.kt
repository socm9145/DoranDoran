package com.purple.core.designsystem.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.purple.core.designsystem.component.HiFilledButton
import com.purple.core.designsystem.component.HiOutlinedButton
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.designsystem.theme.HiTypography

enum class InputType(val question: String, val placeHolder: String) {
    ROOM_NAME("그룹 이름", "ex) 우리 가족"),
    EDIT_ROOM_NAME("변경할 그룹 이름", "ex) 우리 가족"),
    NAME("그룹에서 사용할 이름", "ex) 김둘리 or 아들"),
    EDIT_NAME("변경할 이름", "ex) 이짱구 or 딸"),
    QUESTION_PASSWORD("비밀번호 질문", "ex) 첫째 딸의 생일은?"),
    EDIT_QUESTION_PASSWORD("변경할 비밀번호 질문", "ex) 첫째 아들의 생일은?"),
    CREATE_PASSWORD("비밀번호 입력", "******"),
    EDIT_PASSWORD("변경할 비밀번호 입력", "******"),
}

data class InputData(
    val question: String,
    val placeHolder: String,
    var answer: String,
) {
    constructor(question: String, placeHolder: String) : this(
        question = question,
        placeHolder = placeHolder,
        answer = "",
    )
}

fun createInputDataByInputType(type: InputType, answer: String): InputData {
    return InputData(
        question = type.question,
        placeHolder = type.placeHolder,
        answer = answer,
    )
}

@Composable
fun HiInputDialog(
    questionContent: List<InputData>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    confirmButtonText: String,
    dismissButtonText: String,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = properties,
    ) {
        HiDialogContent(
            questionContent = questionContent,
            onDismiss = onDismiss,
            onConfirm = onConfirm,
            confirmButtonText = confirmButtonText,
            dismissButtonText = dismissButtonText,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HiDialogContent(
    questionContent: List<InputData>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmButtonText: String,
    dismissButtonText: String,
) {
    HiTheme {
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .sizeIn(minWidth = MinWidth, maxWidth = MaxWidth),
        ) {
            Column(
                Modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .padding(DialogPadding),
            ) {
                questionContent.forEach {
                    val (value, setValue) = remember { mutableStateOf(it.answer) }

                    Column(
                        modifier = Modifier
                            .padding(ContentPadding),
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(TitlePadding),
                        ) {
                            Text(
                                color = MaterialTheme.colorScheme.onSurface,
                                text = it.question,
                                style = HiTypography.headlineSmall,
                            )
                        }
                        OutlinedTextField(
                            value = value,
                            onValueChange = { newValue ->
                                setValue(newValue)
                            },
                            placeholder = { Text(text = it.placeHolder) },
                            singleLine = true,
                            maxLines = 1,
                            textStyle = HiTypography.bodyLarge,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
                Row {
                    HiOutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f),
                        text = { Text(text = dismissButtonText) },
                    )
                    HiFilledButton(
                        onClick = onConfirm,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f),
                        text = { Text(text = confirmButtonText) },
                    )
                }
            }
        }
    }
}

private val DialogPadding = PaddingValues(all = 24.dp)
private val TitlePadding = PaddingValues(bottom = 16.dp)
private val ContentPadding = PaddingValues(bottom = 24.dp)
private val MinWidth = 280.dp
private val MaxWidth = 560.dp

@Preview
@Composable
private fun PreviewHiInputDialog() {
    HiInputDialog(
        questionContent = listOf(
            createInputDataByInputType(InputType.ROOM_NAME, answer = ""),
            createInputDataByInputType(InputType.NAME, answer = ""),
            InputData(question = "비밀번호 질문", placeHolder = "비밀번호 입력", answer = ""),
        ),
        onDismiss = {},
        onConfirm = {},
        confirmButtonText = "ok",
        dismissButtonText = "nope",
    )
}
