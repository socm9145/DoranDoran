package com.purple.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HiOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Unspecified,
            contentColor = MaterialTheme.colorScheme.primary,
        ),
        border = BorderStroke(
            width = HiButtonDefaults.OutlinedButtonBorderWidth,
            color = MaterialTheme.colorScheme.primary,
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun HiOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
) {
    HiOutlinedButton(
        onClick = onClick,
        modifier = modifier,
        contentPadding = ButtonDefaults.ContentPadding,
    ) {
        HiButtonContent(
            text = text,
        )
    }
}

@Composable
fun HiFilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun HiFilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
) {
    HiFilledButton(
        onClick = onClick,
        modifier = modifier,
        contentPadding = ButtonDefaults.ContentPadding,
    ) {
        HiButtonContent(
            text = text,
        )
    }
}

@Composable
private fun HiButtonContent(
    text: @Composable () -> Unit,
) {
    Box(
        Modifier
            .padding(0.dp),
    ) {
        text()
    }
}

object HiButtonDefaults {
    val OutlinedButtonBorderWidth = 1.dp
}

@Preview
@Composable
private fun PreviewHiOutlinedButton() {
    HiOutlinedButton(onClick = {}, modifier = Modifier, text = { Text(text = "test button") })
}

@Preview
@Composable
private fun PreviewHiFilledButton() {
    HiFilledButton(onClick = {}, modifier = Modifier, text = { Text(text = "test button") })
}
