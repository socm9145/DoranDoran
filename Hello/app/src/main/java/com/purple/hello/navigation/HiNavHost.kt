package com.purple.hello.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.purple.hello.feature.rooms.navigation.roomsScreen
import com.purple.hello.loading.navigation.loadingNavigationRoute
import com.purple.hello.loading.navigation.loadingScreen
import com.purple.hello.login.navigation.loginScreen
import com.purple.hello.ui.AppState

@Composable
fun HiNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
    startDestination: String = loadingNavigationRoute,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        loadingScreen()
        loginScreen()
        roomsScreen()
    }
}
