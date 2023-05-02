package com.purple.hello.feature.rooms.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.purple.hello.feature.rooms.RoomsRoute

const val roomsNavigationRoute = "rooms_route"

fun NavController.navigateToRooms(navOptions: NavOptions? = null) {
    this.navigate(roomsNavigationRoute, navOptions)
}

fun NavGraphBuilder.roomsScreen() {
    composable(route = roomsNavigationRoute) {
        RoomsRoute()
    }
}
