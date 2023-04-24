package com.purple.hello.feature.rooms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.purple.core.designsystem.theme.HiTheme
import com.purple.hello.domain.feature.model.User
import com.purple.hello.feature.rooms.fake.FakeFactory

@Composable
fun RoomItem(
    roomName: String,
    members: List<User>,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.height(200.dp),
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
            text = members.joinToString(", ") { it.name },
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
                        .crossfade(true)
                        .build(),
                    contentDescription = "${it.name}의 프로필",
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
        RoomItem(
            roomName = room.personalOptions.roomName,
            members = room.members,
            onClick = {},
        )
    }
}
