package com.purple.hello.core.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun FeedCard(
    authorProfileUrl: String?,
    authorName: String,
    headerImageUrl: String?,
    content: String?,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {
        FeedAuthor(
            authorProfileUrl = authorProfileUrl,
            authorName = authorName,
        )
        FeedHeaderImage(
            headerImageUrl = headerImageUrl,
        )
        content?.let { FeedContent(it) }
    }
}

@Composable
private fun FeedHeaderImage(
    headerImageUrl: String?,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(headerImageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth(),
    )
}

@Composable
private fun FeedAuthor(
    modifier: Modifier = Modifier,
    authorProfileUrl: String?,
    authorName: String,
) {
    Row(
        modifier = modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Profile(
            modifier = Modifier.weight(0.1f),
            profileImageUrl = authorProfileUrl,
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            modifier = Modifier.weight(0.9f),
            text = authorName,
        )
    }
}

@Composable
private fun FeedContent(
    content: String,
) {
    Text(
        modifier = Modifier.padding(12.dp),
        text = content,
    )
}

@Preview
@Composable
private fun PreviewFeedCard() {
    val profileUrl = "https://www.adobe.com/content/dam/cc/us/en/creativecloud/photography/discover/cut-out-an-image/thumbnail.jpeg"
    val headerImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXmahkZs_xLbHV9z0jseo7GB74PXBrp95xJHOM2w3QI8w_XV_FZYAkga8j_O9BHEBWEYo&usqp=CAU"
    val authorName = "아무개"
    val content = "산 정상"

    FeedCard(
        authorProfileUrl = profileUrl,
        headerImageUrl = headerImageUrl,
        authorName = authorName,
        content = content,
    )
}
