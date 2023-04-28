package com.vpnt.saves.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.vpnt.saves.ui.profile.ProfileScreen

const val profileRoute = "profile_route"

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    this.navigate(profileRoute, navOptions)
}

fun NavGraphBuilder.profileScreen() {
    composable(route = profileRoute) {
        ProfileScreen()
    }
}
