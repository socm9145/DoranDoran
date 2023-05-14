package com.purple.hello.feature.rooms.view

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.purple.core.model.Feed
import com.purple.core.model.Member

@Composable
internal fun FeedItem(
    feed: Feed
) {
    Profile(author = feed.author)
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(feed.headerImageUrl)
            .crossfade(true)
            .build(),
        contentDescription = "",
    )
}

@Composable
private fun Profile(
    author: Member
) {
    Row {
        Text(text = author.nickName)
    }
}
