package com.purple.hello.feature.notification.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.purple.hello.feature.notification.NotificationRoute

const val notificationRoute = "alarm_route"

fun NavController.navigateToNotification(navOptions: NavOptions? = null) {
    this.navigate(notificationRoute, navOptions)
}

fun NavGraphBuilder.notificationScreen(
    onBackClick: () -> Unit,
) {
    composable(route = notificationRoute) {
        NotificationRoute(
            onBackClick = onBackClick,
        )
    }
}
