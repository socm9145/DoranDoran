package com.purple.hello.feature.rooms

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
) {
    val roomDetailUiState by roomDetailViewModel.roomDetailUiState.collectAsState()

    Column {
        RoomDetailScreen(roomDetailUiState = roomDetailUiState)
    }
}

@Composable
private fun RoomDetailScreen(
    roomDetailUiState: RoomDetailUiState,
) {
    when (roomDetailUiState) {
        is RoomDetailUiState.Success -> {
            Column {
                RoomDetailAppBar(roomDetailUiState.roomDetail.roomName)
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
) {
    HiTopAppBar(
        title = roomName,
        navigationIcon = HiIcons.ArrowBack,
        navigationIconContentDescription = "뒤로가기",
        onNavigationClick = { /*TODO*/ },
        actions = {
            HiIconButton(
                onClick = { /*TODO*/ },
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
