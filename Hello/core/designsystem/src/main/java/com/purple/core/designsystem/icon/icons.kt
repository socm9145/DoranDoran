package com.purple.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector

object HiIcons {

    val AddRoom = Icons.Rounded.Add
    val ArrowBack = Icons.Rounded.ArrowBack
    val Notifications = Icons.Rounded.Notifications
    val MoreVert = Icons.Rounded.MoreVert
    val Settings = Icons.Rounded.Settings
    val ArrowRight = Icons.Rounded.KeyboardArrowRight
    val Calendar = Icons.Rounded.DateRange
    val PersonAdd = Icons.Rounded.Send
}

sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}
