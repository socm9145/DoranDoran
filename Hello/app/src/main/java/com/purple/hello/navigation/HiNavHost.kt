package com.purple.hello.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.purple.hello.feature.rooms.navigation.roomsGraph
import com.purple.hello.feature.rooms.navigation.roomsNavigationRoute
import com.purple.hello.feature.setting.app.navigation.appSettingScreen
import com.purple.hello.feature.setting.app.navigation.navigateToAppSetting
import com.purple.hello.feature.setting.room.navigation.navigateToRoomSetting
import com.purple.hello.feature.setting.room.navigation.roomSettingScreen
import com.purple.hello.ui.AppState

@Composable
fun HiNavHost(
    appState: AppState.LoggedIn,
    modifier: Modifier = Modifier,
    startDestination: String = roomsNavigationRoute,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        roomsGraph(
            navController = navController,
            onBackClick = {
                navController.popBackStack()
            },
            onClickAppSetting = {
                navController.navigateToAppSetting()
            },
            onClickRoomSetting = {
                navController.navigateToRoomSetting()
            },
        )
        appSettingScreen(
            onBackClick = {
                navController.popBackStack()
            },
        )
        roomSettingScreen(
            onBackClick = {
                navController.popBackStack()
            },
        )
    }
}
