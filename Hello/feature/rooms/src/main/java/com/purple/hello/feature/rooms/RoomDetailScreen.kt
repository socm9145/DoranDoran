package com.purple.hello.feature.rooms

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.purple.core.designsystem.component.HiIconButton
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.model.Member
import com.purple.hello.feature.rooms.state.RoomDetailUiState
import com.purple.hello.feature.rooms.view.MemberProfileItem
import com.purple.hello.feature.rooms.viewmodel.RoomDetailViewModel

@Composable
internal fun RoomDetailRoute(
    roomDetailViewModel: RoomDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onClickRoomSetting: (roomId: Long) -> Unit,
) {
    val roomDetailUiState by roomDetailViewModel.roomDetailUiState.collectAsState()
    val roomCode by roomDetailViewModel.roomCode.collectAsState()

    Column {
        RoomDetailScreen(
            roomDetailUiState = roomDetailUiState,
            onBackClick = onBackClick,
            onClickRoomSetting = {
                onClickRoomSetting(it)
            },
            roomCode = roomCode,
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
                    roomDetailUiState.roomDetail.roomName,
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
