package com.purple.hello.feature.rooms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.purple.core.designsystem.component.HiOverlayLoadingWheel
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.designsystem.theme.LocalGradientColors
import com.purple.hello.feature.rooms.fake.FakeFactory
import com.purple.hello.feature.rooms.state.RoomsUiState

@Composable
internal fun RoomsRoute(
    uiState: RoomsUiState,
) {
    val isRoomsLoading = uiState is RoomsUiState.Loading
    val state = rememberLazyGridState()

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
    AnimatedVisibility(visible = isRoomsLoading) {
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
}

private fun LazyGridScope.roomsScreen(
    roomsState: RoomsUiState,
) {
    when (roomsState) {
        RoomsUiState.Loading -> Unit
        is RoomsUiState.Loaded -> {
            items(roomsState.rooms, key = { it.id }) { room ->
                RoomItem(
                    roomName = room.name,
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
            RoomsRoute(
                uiState = RoomsUiState.Loaded(
                    rooms = FakeFactory.makeRooms(),
                ),
            )
        }
    }
}
