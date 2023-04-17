package com.purple.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector

object HiIcons {

    val ArrowBack = Icons.Rounded.ArrowBack
    val Notifications = Icons.Rounded.Notifications
    val MoreVert = Icons.Rounded.MoreVert
    val Settings = Icons.Rounded.Settings
    val ArrowRight = Icons.Rounded.KeyboardArrowRight

    // CalendarMonth
    val DateRange = Icons.Rounded.DateRange

    // PersonAdd
    val Send = Icons.Rounded.Send
}

sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}
