package com.purple.hello.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.purple.hello.domain.account.CheckLoggedInUseCase
import com.purple.hello.domain.account.GetUserIdUseCase
import com.purple.hello.feature.rooms.navigation.navigateToRooms
import com.purple.hello.feature.rooms.navigation.roomsNavigationRoute
import kotlinx.coroutines.flow.first
import kotlin.properties.Delegates

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberAnimatedNavController(),
    checkLoggedInUseCase: CheckLoggedInUseCase,
    getUserIdUseCase: GetUserIdUseCase,
): MutableState<AppState> {
    val appState = remember(navController, windowSizeClass) {
        mutableStateOf<AppState>(AppState.Init(windowSizeClass))
    }

    LaunchedEffect(checkLoggedInUseCase) {
        checkLoggedInUseCase().collect { isLoggedIn ->
            appState.value = when (isLoggedIn) {
                true -> {
                    AppState.LoggedIn(navController, windowSizeClass).apply {
                        userId = getUserIdUseCase().first()
                    }
                }
                false -> AppState.LoggedOut(windowSizeClass)
            }
        }
    }

    return appState
}

@Stable
sealed class AppState(
    val windowSizeClass: WindowSizeClass,
) {

    class Init(
        windowSizeClass: WindowSizeClass,
    ) : AppState(windowSizeClass)

    class LoggedIn(
        val navController: NavHostController,
        windowSizeClass: WindowSizeClass,
    ) : AppState(windowSizeClass) {
        val currentDestination: NavDestination?
            @Composable get() = navController
                .currentBackStackEntryAsState().value?.destination

        var userId by Delegates.notNull<Long>()

        private fun navigateToDestination(destination: String) {
            when (destination) {
                roomsNavigationRoute -> navController.navigateToRooms()
            }
        }
    }

    class LoggedOut(
        windowSizeClass: WindowSizeClass,
    ) : AppState(windowSizeClass)
}
