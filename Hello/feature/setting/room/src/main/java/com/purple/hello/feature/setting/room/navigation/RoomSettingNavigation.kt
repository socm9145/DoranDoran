package com.purple.hello.feature.setting.room.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.purple.hello.feature.setting.room.RoomSettingRoute

const val roomSettingNavigationRoute = "room_setting_route"

fun NavController.navigateToRoomSetting(navOptions: NavOptions? = null) {
    this.navigate(roomSettingNavigationRoute, navOptions)
}

fun NavGraphBuilder.roomSettingScreen(
    onBackClick: () -> Unit,
) {
    composable(route = roomSettingNavigationRoute) {
        RoomSettingRoute(
            // TODO : isHost 변경!!!!!!!!!!!!!
            isHost = true,
            onBackClick = onBackClick,
        )
    }
}
