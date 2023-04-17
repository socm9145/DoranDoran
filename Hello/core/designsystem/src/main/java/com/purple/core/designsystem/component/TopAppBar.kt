package com.purple.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.purple.core.designsystem.icon.HiIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiTopAppBar(
    title: String,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String?,
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Unspecified,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = modifier
            .testTag("hiTopAppBar"),
    )
}

@Preview
@Composable
private fun PreviewHiTopAppBar() {
    HiTopAppBar(
        title = "test title",
        navigationIcon = HiIcons.ArrowBack,
        navigationIconContentDescription = "뒤로가기",
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = HiIcons.PersonAdd, contentDescription = "초대하기")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = HiIcons.Calendar, contentDescription = "달력")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = HiIcons.Settings, contentDescription = "설정")
            }
        },
    )
}
