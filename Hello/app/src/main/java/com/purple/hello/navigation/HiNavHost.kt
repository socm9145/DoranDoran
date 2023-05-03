package com.purple.hello.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.purple.hello.feature.rooms.navigation.roomsScreen
import com.purple.hello.login.navigation.loginNavigationRoute
import com.purple.hello.login.navigation.loginScreen
import com.purple.hello.ui.AppState

@Composable
fun HiNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
    startDestination: String = loginNavigationRoute,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        loginScreen()
        roomsScreen()
    }
}
