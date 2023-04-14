package com.purple.core.designsystem

import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiBadge(
    content: String,
) {
    Badge(
        containerColor = MaterialTheme.colorScheme.error,
        contentColor = MaterialTheme.colorScheme.onError,
        content = { Text(text = content) },
    )
}

@Preview
@Composable
private fun PreviewBadge() {
    MaterialTheme {
        HiBadge(content = "123")
    }
}
