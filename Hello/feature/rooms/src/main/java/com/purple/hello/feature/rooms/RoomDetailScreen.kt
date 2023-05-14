package com.purple.hello.feature.rooms

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.purple.core.designsystem.component.HiIconButton
import com.purple.core.designsystem.component.HiOutlinedButton
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.model.Member
import com.purple.core.model.Room
import com.purple.hello.feature.rooms.state.FeedUiState
import com.purple.hello.feature.rooms.view.FeedItem
import com.purple.hello.feature.rooms.view.MemberProfileItem
import com.purple.hello.feature.rooms.viewmodel.FeedViewModel
import com.purple.hello.feature.rooms.viewmodel.RoomsViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun RoomDetailRoute(
    feedViewModel: FeedViewModel = hiltViewModel(),
    roomsViewModel: RoomsViewModel = hiltViewModel(),
    onClickCameraButton: (roomId: Long) -> Unit,
    onClickRoomSetting: (roomId: Long) -> Unit,
    onBackClick: () -> Unit,
) {
    val selectedRoom by roomsViewModel.selectedRoom.collectAsState()
    val roomCode by roomsViewModel.roomCode.collectAsState()
    val feedUiState by feedViewModel.feedUiState.collectAsState()

    val currentDate = remember { mutableStateOf(LocalDateTime.of(2023, 5, 14,0, 0)) }

    if(selectedRoom != null) {
        LaunchedEffect(currentDate) {
            feedViewModel.selectDate(currentDate.value)
            feedViewModel.fetchFeed(selectedRoom!!.roomId, currentDate.value)
        }

        LaunchedEffect(selectedRoom) {
            currentDate.value = LocalDateTime.now()
            feedViewModel.fetchFeed(selectedRoom!!.roomId, currentDate.value)
            roomsViewModel.fetchRoomDetail()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if(selectedRoom != null) {
            RoomDetailScreen(
                roomDetail = selectedRoom!!,
                onBackClick = onBackClick,
                onClickRoomSetting = {
                    onClickRoomSetting(it)
                },
                roomCode = roomCode,
            )
            if (
                feedUiState is FeedUiState.Success &&
                currentDate.value.toLocalDate() == LocalDate.now() &&
                (feedUiState as FeedUiState.Success).isPossibleToUpload
            ) {
                OpenCameraButton(
                    onClick = {
                        onClickCameraButton(selectedRoom!!.roomId)
                    },
                )
            }
            RoomFeedScreen(
                feedUiState = feedUiState,
            )
        }
    }
}

@Composable
private fun RoomDetailScreen(
    roomDetail: Room,
    onBackClick: () -> Unit,
    onClickRoomSetting: (roomId: Long) -> Unit,
    roomCode: String,
) {
    Column {
        RoomDetailAppBar(
            onBackClick = onBackClick,
            onClickRoomSetting = {
                onClickRoomSetting(
                    roomDetail.roomId,
                )
            },
            roomName = roomDetail.roomName,
            roomCode = roomCode,
        )
        MembersViewInGroup(roomDetail.members)
    }
}

@Composable
private fun RoomDetailAppBar(
    roomName: String,
    onBackClick: () -> Unit,
    onClickRoomSetting: () -> Unit,
    roomCode: String,
) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, roomCode)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current

    HiTopAppBar(
        title = roomName,
        navigationIcon = HiIcons.ArrowBack,
        navigationIconContentDescription = "뒤로가기",
        onNavigationClick = { onBackClick() },
        actions = {
            HiIconButton(
                onClick = {
                    context.startActivity(shareIntent)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = HiIcons.PersonAdd),
                        contentDescription = "공유하기",
                    )
                },
            )
            HiIconButton(
                onClick = { onClickRoomSetting() },
                icon = {
                    Icon(
                        imageVector = HiIcons.Settings,
                        contentDescription = "설정",
                    )
                },
            )
        },
    )
}

@Composable
private fun RoomFeedScreen(
    feedUiState: FeedUiState,
) {
    when (feedUiState) {
        is FeedUiState.Error -> {
        }
        is FeedUiState.Loading -> {
        }
        is FeedUiState.Success -> {
            LazyColumn() {
                items(feedUiState.feeds, key = { it.feedId }) {
                    FeedItem(feed = it)
                }
            }
        }
    }
}

@Composable
private fun MembersViewInGroup(
    members: List<Member>,
) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(members, key = { it.id }) { member ->
            MemberProfileItem(member = member)
        }
    }
}

@Composable
private fun OpenCameraButton(
    onClick: () -> Unit,
) {
    HiOutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        text = {
            Text(
                text = "사진 찍어서 올리기",
                style = MaterialTheme.typography.bodyMedium,
            )
        },
    )
}
