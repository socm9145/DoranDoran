package com.purple.core.designsystem.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.purple.core.designsystem.component.HiFilledButton
import com.purple.core.designsystem.component.HiOutlinedButton
import com.purple.core.designsystem.theme.HiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiInputDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    currentValue: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    confirmButtonText: String,
    dismissButtonText: String,
) {
    val (value, setValue) = remember { mutableStateOf(currentValue) }

    HiTheme() {
        AlertDialog(
            onDismissRequest = onDismiss,
            modifier = modifier,
            title = { Text(text = title) },
            text = {
                OutlinedTextField(
                    value = value,
                    onValueChange = { newTitle ->
                        setValue(newTitle)
                        onValueChange(newTitle)
                    },
                    placeholder = placeholder,
                    singleLine = true,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            containerColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            confirmButton = {
                HiFilledButton(
                    onClick = onConfirm,
                    text = { Text(text = confirmButtonText) },
                )
            },
            dismissButton = {
                HiOutlinedButton(
                    onClick = onDismiss,
                    text = { Text(text = dismissButtonText) },
                )
            },
        )
    }
}

@Preview
@Composable
private fun PreviewHiInputDialog() {
    HiInputDialog(
        onDismiss = {},
        onConfirm = {},
        modifier = Modifier,
        title = "test question",
        currentValue = "existing data",
        onValueChange = {},
        placeholder = { Text(text = "placeholder") },
        confirmButtonText = "ok",
        dismissButtonText = "nope",
    )
}
