package com.purple.hello.feature.rooms

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.purple.hello.feature.rooms.state.FeedUiState
import com.purple.hello.feature.rooms.state.RoomDetailUiState
import com.purple.hello.feature.rooms.view.MemberProfileItem
import com.purple.hello.feature.rooms.viewmodel.RoomDetailViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun RoomDetailRoute(
    roomDetailViewModel: RoomDetailViewModel = hiltViewModel(),
    onClickCameraButton: (roomId: Long) -> Unit,
    onClickRoomSetting: (roomId: Long) -> Unit,
    onBackClick: () -> Unit,
) {
    val roomDetailUiState by roomDetailViewModel.roomDetailUiState.collectAsState()
    val feedUiState by roomDetailViewModel.feedUiState.collectAsState()
    val roomCode by roomDetailViewModel.roomCode.collectAsState()

    val currentDate = remember { mutableStateOf(LocalDateTime.now()) }

    LaunchedEffect(currentDate) {
        roomDetailViewModel.selectDate(currentDate.value)
        roomDetailViewModel.fetchFeed(currentDate.value)
    }

    Column {
        RoomDetailScreen(
            roomDetailUiState = roomDetailUiState,
            onBackClick = onBackClick,
            onClickRoomSetting = {
                onClickRoomSetting(it)
            },
            roomCode = roomCode,
        )
        if (
            roomDetailUiState is RoomDetailUiState.Success &&
            feedUiState is FeedUiState.Success &&
            currentDate.value.toLocalDate() == LocalDate.now() &&
            (feedUiState as FeedUiState.Success).isPossibleToUpload
        ) {
            OpenCameraButton(
                onClick = {
                    onClickCameraButton((roomDetailUiState as RoomDetailUiState.Success).roomDetail.roomId)
                },
            )
        }
        RoomFeedScreen(
            feedUiState = feedUiState,
        )
    }
}

@Composable
private fun RoomDetailScreen(
    roomDetailUiState: RoomDetailUiState,
    onBackClick: () -> Unit,
    onClickRoomSetting: (roomId: Long) -> Unit,
    roomCode: String,
) {
    when (roomDetailUiState) {
        is RoomDetailUiState.Success -> {
            Column {
                RoomDetailAppBar(
                    onBackClick = onBackClick,
                    onClickRoomSetting = {
                        onClickRoomSetting(
                            roomDetailUiState.roomDetail.roomId,
                        )
                    },
                    roomName = roomDetailUiState.roomDetail.roomName,
                    roomCode = roomCode,
                )
                MembersViewInGroup(roomDetailUiState.roomDetail.members)
            }
        }
        is RoomDetailUiState.Error -> Unit
        is RoomDetailUiState.Loading -> Unit
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
                items(feedUiState.feeds, key = { it.author.id }) {
                    Text(text = it.author.nickName)
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
