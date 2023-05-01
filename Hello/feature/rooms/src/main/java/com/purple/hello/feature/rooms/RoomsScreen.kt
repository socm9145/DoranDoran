package com.purple.hello.feature.rooms

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.purple.core.designsystem.component.HiIconButton
import com.purple.core.designsystem.component.HiOverlayLoadingWheel
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.designsystem.theme.LocalGradientColors
import com.purple.hello.feature.rooms.state.RoomsUiState
import com.purple.hello.feature.rooms.viewmodel.RoomsViewModel

@Composable
internal fun RoomsRoute(
    roomsViewModel: RoomsViewModel = hiltViewModel(),
) {
    val uiState by roomsViewModel.roomsUiState.collectAsState()
    val isRoomsLoading = uiState is RoomsUiState.Loading
    val state = rememberLazyGridState()

    AnimatedVisibility(
        visible = isRoomsLoading,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
        ) + fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            HiOverlayLoadingWheel(
                contentDesc = "loading",
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
    Column {
        RoomsAppBar()
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            LazyVerticalGrid(
                columns = Adaptive(300.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxWidth(),
                state = state,
            ) {
                roomsScreen(roomsState = uiState)
            }
            FloatingActionButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp),
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = HiIcons.AddRoom,
                    contentDescription = "addRoom",
                )
            }
        }
    }
}

@Composable
private fun RoomsAppBar() {
    HiTopAppBar(
        title = "도란도란",
        navigationIcon = HiIcons.PersonAdd,
        navigationIconContentDescription = "",
        actions = {
            HiIconButton(
                onClick = { /*TODO*/ },
                icon = {
                    Icon(
                        imageVector = HiIcons.Notifications,
                        contentDescription = "",
                    )
                },
            )
            HiIconButton(
                onClick = { /*TODO*/ },
                icon = {
                    Icon(
                        imageVector = HiIcons.MoreVert,
                        contentDescription = "",
                    )
                },
            )
        },
    )
}

private fun LazyGridScope.roomsScreen(
    roomsState: RoomsUiState,
) {
    when (roomsState) {
        RoomsUiState.Loading -> Unit
        is RoomsUiState.Success -> {
            items(roomsState.rooms, key = { it.roomId }) { room ->
                RoomItem(
                    roomName = room.personalOptions.roomName,
                    members = room.members,
                    onClick = {},
                )
            }
        }
        is RoomsUiState.Error -> Unit
    }
}

@Composable
private fun ErrorScreen() {
}

@Preview
@Composable
private fun PreviewRoomScreen() {
    HiTheme {
        Box(
            Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(LocalGradientColors.current.top, LocalGradientColors.current.bottom),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY,
                    ),
                )
                .fillMaxSize(),
        ) {
            RoomsRoute()
        }
    }
}
