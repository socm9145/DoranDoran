package com.purple.hello.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.purple.hello.feature.rooms.navigation.navigateToRooms
import com.purple.hello.feature.rooms.navigation.roomsGraph
import com.purple.hello.feature.rooms.navigation.roomsNavigationRoute
import com.purple.hello.feature.setting.app.navigation.appSettingScreen
import com.purple.hello.feature.setting.app.navigation.navigateToAppSetting
import com.purple.hello.feature.setting.profile.navigation.navigateToProfileSetting
import com.purple.hello.feature.setting.profile.navigation.profileSettingScreen
import com.purple.hello.feature.setting.room.navigation.navigateToRoomSetting
import com.purple.hello.feature.setting.room.navigation.roomSettingScreen
import com.purple.hello.ui.AppState

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun HiNavHost(
    appState: AppState.LoggedIn,
    modifier: Modifier = Modifier,
    startDestination: String = roomsNavigationRoute,
) {
    val navController = appState.navController
    val userId = appState.userId

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        roomsGraph(
            userId = userId,
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
                navController.navigateToRooms(
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(roomsNavigationRoute, true)
                        .build(),
                )
            },
        )
    }
}
