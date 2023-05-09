package com.purple.hello.feature.rooms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.model.Member
import com.purple.hello.feature.groups.R
import com.purple.hello.feature.rooms.fake.FakeFactory

const val DEFAULT_PROFILE = ""

@Composable
fun RoomItem(
    roomName: String,
    members: List<Member>,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .height(200.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
    ) {
        Text(
            modifier = Modifier.padding(24.dp),
            text = roomName,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = members.joinToString(", ") { it.nickName },
            style = MaterialTheme.typography.bodyLarge,
        )
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            members.forEach {
                AsyncImage(
                    modifier = Modifier.weight(1f),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it.profileUrl)
                        .placeholder(R.drawable.profile_placeholder)
                        .error(R.drawable.profile_placeholder)
                        .crossfade(true)
                        .build(),
                    contentDescription = "${it.nickName}의 프로필",
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRoomItem() {
    val room = FakeFactory.makeRoom(1, "우리집")
    HiTheme {
//        RoomItem(
//            roomName = room.roomName,
//            members = room.members,
//            onClick = {},
//        )
    }
}
