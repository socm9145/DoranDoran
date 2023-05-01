package com.purple.core.designsystem.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.purple.core.designsystem.component.HiFilledButton
import com.purple.core.designsystem.component.HiOutlinedButton
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.designsystem.theme.HiTypography

enum class InputType(val question: String, val placeHolder: String, val supportingText: String) {
    ROOM_NAME("그룹 이름", "ex) 우리 가족", "내가 보고 싶은 그룹 이름을 입력해주세요"),
    EDIT_ROOM_NAME("변경할 그룹 이름", "ex) 우리 가족", "변경할 그룹 이름을 입력해주세요"),
    NAME("그룹에서 사용할 이름", "ex) 김둘리 or 아들", "이름을 입력해주세요"),
    EDIT_NAME("변경할 이름", "ex) 이짱구 or 딸", "변경할 이름을 입력해주세요"),
    QUESTION_PASSWORD("비밀번호 질문", "ex) 첫째 딸의 생일은?", "그룹과 관련된 질문을 입력해주세요"),
    EDIT_QUESTION_PASSWORD("변경할 비밀번호 질문", "ex) 첫째 아들의 생일은?", "변경할 질문을 입력해주세요"),
    CREATE_PASSWORD("비밀번호 입력", "******", "비밀번호를 입력해주세요"),
    EDIT_PASSWORD("변경할 비밀번호 입력", "******", "변경할 비밀번호를 입력해주세요"),
}

data class InputData(
    val question: String,
    val placeHolder: String,
    val supportingText: String,
    var answer: String,
) {
    constructor(question: String, placeHolder: String, supportingText: String) : this(
        question = question,
        placeHolder = placeHolder,
        supportingText = supportingText,
        answer = "",
    )
}

fun createInputDataByInputType(type: InputType, answer: String): InputData {
    return InputData(
        question = type.question,
        placeHolder = type.placeHolder,
        supportingText = type.supportingText,
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
fun HiDialogContent(
    questionContent: List<InputData>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmButtonText: String,
    dismissButtonText: String,
) {
    val questionContentList = remember {
        questionContent.map { input -> mutableStateOf(input.answer.orEmpty()) }
    }
    val isConfirmEnabled = questionContentList.all { it.value.isNotEmpty() }

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
                questionContent.forEachIndexed { index, input ->
                    val inputText = questionContentList[index]
                    val isError = inputText.value.isEmpty()
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
                                text = input.question,
                                style = HiTypography.headlineSmall,
                            )
                        }
                        OutlinedTextField(
                            value = inputText.value,
                            onValueChange = { newInputText ->
                                inputText.value = newInputText
                                questionContent[index].answer = newInputText
                            },
                            placeholder = { Text(text = input.placeHolder) },
                            supportingText = { Text(text = input.supportingText) },
                            singleLine = true,
                            maxLines = 1,
                            textStyle = HiTypography.bodyLarge,
                            isError = isError,
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
                        enabled = isConfirmEnabled,
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
            createInputDataByInputType(InputType.ROOM_NAME, answer = "기존 데이터"),
            createInputDataByInputType(InputType.NAME, answer = "기존 데이터"),
            InputData(
                question = "비밀번호 질문",
                placeHolder = "비밀번호 입력",
                supportingText = "질문에 맞는 비밀번호를 입력해주세요",
                answer = "",
            ),
        ),
        onDismiss = {},
        onConfirm = {},
        confirmButtonText = "ok",
        dismissButtonText = "nope",
    )
}
