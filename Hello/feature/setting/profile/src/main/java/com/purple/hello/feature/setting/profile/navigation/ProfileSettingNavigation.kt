package com.purple.hello.feature.setting.profile.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.purple.hello.feature.setting.profile.ProfileSettingRoute

const val profileSettingNavigationRoute = "profile_setting_route"

fun NavController.navigateToProfileSetting(navOptions: NavOptions? = null) {
    this.navigate(profileSettingNavigationRoute, navOptions)
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.profileSettingScreen(
    onBackClick: () -> Unit,
    onClickRooms: () -> Unit,
) {
    composable(route = profileSettingNavigationRoute) {
        ProfileSettingRoute(
            onBackClick = onBackClick,
            onClickRooms = onClickRooms,
        )
    }
}
