package com.purple.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.purple.core.designsystem.icon.HiIcons

@Composable
fun HiIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Unspecified,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun HiIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
) {
    HiIconButton(
        onClick = onClick,
        modifier = modifier,
        contentPadding = ButtonDefaults.TextButtonContentPadding,
    ) {
        HiButtonContent(
            Icon = icon,
        )
    }
}

@Composable
private fun HiButtonContent(
    Icon: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier.padding(start = 0.dp),
    ) {
        Icon()
    }
}

@Preview
@Composable
fun PreviewIconButton() {
    HiIconButton(
        onClick = {},
        icon = {
            Icon(
                imageVector = HiIcons.Calendar,
                contentDescription = "달력 확인",
            )
        },
    )
}
