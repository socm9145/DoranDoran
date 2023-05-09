package com.purple.hello.feature.rooms.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.purple.hello.feature.rooms.RoomDetailRoute
import com.purple.hello.feature.rooms.RoomsRoute

internal const val roomIdArg = "roomIdArg"

const val roomsNavigationRoute = "rooms_route"
const val roomsListRoute = "room_list_route"
const val roomDetailRoute = "room_detail_route"

fun NavController.navigateToRooms(navOptions: NavOptions? = null) {
    this.navigate(roomsListRoute, navOptions)
}

fun NavController.navigateRoomDetail(roomId: Long, navOptions: NavOptions? = null) {
    this.navigate("$roomDetailRoute/$roomId", navOptions)
}

fun NavGraphBuilder.roomsGraph(
    navController: NavController,
    onBackClick: () -> Unit,
    onClickAppSetting: () -> Unit,
    onClickRoomSetting: (roomId: Long) -> Unit,
) {
    navigation(
        startDestination = roomsListRoute,
        route = roomsNavigationRoute,
    ) {
        composable(route = roomsListRoute) {
            RoomsRoute(
                onClickRoom = {
                    navController.navigateRoomDetail(it)
                },
                onClickAppSetting = {
                    onClickAppSetting()
                },
            )
        }
        composable(
            route = "$roomDetailRoute/{$roomIdArg}",
            arguments = listOf(navArgument(roomIdArg) { type = NavType.LongType }),
        ) {
            RoomDetailRoute(
                onBackClick = {
                    onBackClick()
                },
                onClickRoomSetting = {
                    onClickRoomSetting(it)
                },
            )
        }
    }
}
