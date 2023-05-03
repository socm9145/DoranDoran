package com.purple.hello.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.purple.hello.domain.account.CheckLoggedInUseCase
import com.purple.hello.feature.rooms.navigation.navigateToRooms
import com.purple.hello.feature.rooms.navigation.roomsNavigationRoute
import com.purple.hello.login.navigation.loginNavigationRoute
import com.purple.hello.login.navigation.navigateToLogin

@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),
    checkLoggedInUseCase: CheckLoggedInUseCase,
): AppState {
    val appState = remember(navController, windowSizeClass) {
        AppState(navController, windowSizeClass)
    }
    LaunchedEffect(checkLoggedInUseCase) {
        checkLoggedInUseCase().collect {
            appState.setLoggedIn(it)
        }
    }

    return appState
}

@Stable
class AppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass,
) {
    private var isLoggedIn: MutableState<Boolean> = mutableStateOf(false)

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    fun setLoggedIn(isLoggedIn: Boolean) {
        this.isLoggedIn.value = isLoggedIn
        navigateToDestination(
            if (isLoggedIn) roomsNavigationRoute else loginNavigationRoute,
        )
    }

    private fun navigateToDestination(destination: String) {
        when (destination) {
            roomsNavigationRoute -> navController.navigateToRooms()
            loginNavigationRoute -> navController.navigateToLogin()
        }
    }
}
