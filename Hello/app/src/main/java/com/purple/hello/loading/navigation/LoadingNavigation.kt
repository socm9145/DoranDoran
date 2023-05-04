package com.purple.hello.loading.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.purple.hello.loading.LoadingScreen

const val loadingNavigationRoute = "loading_route"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(loadingNavigationRoute, navOptions)
}

fun NavGraphBuilder.loadingScreen() {
    composable(route = loadingNavigationRoute) {
        LoadingScreen()
    }
}
