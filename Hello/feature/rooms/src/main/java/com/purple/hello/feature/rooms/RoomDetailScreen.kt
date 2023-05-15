package com.purple.hello.feature.rooms

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    val currentDate = remember { mutableStateOf(LocalDateTime.now()) }

    if (selectedRoom != null) {
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
            .background(MaterialTheme.colorScheme.background),
    ) {
        if (selectedRoom != null) {
            RoomDetailScreen(
                roomDetail = selectedRoom!!,
                onBackClick = onBackClick,
                onClickRoomSetting = {
                    onClickRoomSetting(it)
                },
                roomCode = roomCode,
            )
            Divider()
            Question(feedUiState, currentDate.value)
            Divider()
            OpenCameraButton(
                feedUiState = feedUiState,
                onClick = {
                    onClickCameraButton(selectedRoom!!.roomId)
                },
                date = currentDate.value,
            )
            RoomFeedScreen(
                feedUiState = feedUiState,
            )
        }
    }
}

@Composable
private fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(Color.LightGray),
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Question(feedUiState: FeedUiState, date: LocalDateTime) {
    if (feedUiState is FeedUiState.Success) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "${date.monthValue}월 ${date.dayOfMonth}일의 질문",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = feedUiState.question ?: "아직 질문이 올라오지 않았어요",
                style = MaterialTheme.typography.bodyLarge,
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
    val state = rememberLazyListState()

    when (feedUiState) {
        is FeedUiState.Error -> {
        }
        is FeedUiState.Loading -> {
        }
        is FeedUiState.Success -> {
            LazyColumn(
                state = state,
            ) {
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
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(members, key = { it.id }) { member ->
            MemberProfileItem(member = member)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun OpenCameraButton(
    feedUiState: FeedUiState,
    date: LocalDateTime,
    onClick: () -> Unit,
) {
    if (feedUiState is FeedUiState.Success) {
        if (
            date.toLocalDate() == LocalDate.now() &&
            feedUiState.isPossibleToUpload &&
            feedUiState.question != null
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
    }
}
