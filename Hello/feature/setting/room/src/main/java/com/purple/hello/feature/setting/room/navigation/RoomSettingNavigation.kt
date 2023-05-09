package com.purple.hello.feature.setting.room.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.purple.hello.feature.setting.room.RoomSettingRoute

internal const val roomIdArg = "roomId"
const val roomSettingNavigationRoute = "room_setting_route"

fun NavController.navigateToRoomSetting(navOptions: NavOptions? = null, roomId: Long) {
    this.navigate("$roomSettingNavigationRoute/$roomId", navOptions)
}

fun NavGraphBuilder.roomSettingScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = "$roomSettingNavigationRoute/{$roomIdArg}",
        arguments = listOf(
            navArgument(roomIdArg) { type = NavType.LongType },
        ),
    ) {
        RoomSettingRoute(
            onBackClick = onBackClick,
        )
    }
}
