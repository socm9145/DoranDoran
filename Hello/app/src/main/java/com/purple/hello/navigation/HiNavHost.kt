package com.purple.hello.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.purple.hello.feature.notification.navigation.navigateToNotification
import com.purple.hello.feature.notification.navigation.notificationScreen
import com.purple.hello.feature.rooms.navigation.roomsGraph
import com.purple.hello.feature.rooms.navigation.roomsListRoute
import com.purple.hello.feature.rooms.navigation.roomsNavigationRoute
import com.purple.hello.feature.setting.app.navigation.appSettingScreen
import com.purple.hello.feature.setting.app.navigation.navigateToAppSetting
import com.purple.hello.feature.setting.profile.navigation.navigateToProfileSetting
import com.purple.hello.feature.setting.profile.navigation.profileSettingScreen
import com.purple.hello.feature.setting.room.navigation.navigateToRoomSetting
import com.purple.hello.feature.setting.room.navigation.roomSettingScreen
import com.purple.hello.ui.AppState

@RequiresApi(Build.VERSION_CODES.R)
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
                navController.navigateToRoomSetting(roomId = it)
            },
            onClickNotification = {
                navController.navigateToNotification()
            },
        )
        appSettingScreen(
            onBackClick = {
                navController.popBackStack()
            },
            clearNav = {
                navController.popBackStack()
            },
            onClickProfileSetting = {
                navController.navigateToProfileSetting()
            },
        )
        roomSettingScreen(
            onBackClick = {
                navController.popBackStack()
            },
        )
        profileSettingScreen(
            onBackClick = {
                navController.popBackStack()
            },
            onClickRooms = {
                navController.popBackStack(
                    route = roomsListRoute,
                    inclusive = true,
                )
            },
        )
        notificationScreen(
            onBackClick = {
                navController.popBackStack()
            },
        )
    }
}
