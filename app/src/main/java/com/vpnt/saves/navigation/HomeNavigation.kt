package com.vpnt.saves.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.vpnt.saves.ui.home.HomeRoute

const val homeRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    if (this.currentDestination?.route != homeRoute) {
        this.navigate(homeRoute, navOptions)
    }
}

fun NavGraphBuilder.homeScreen() {
    composable(route = homeRoute) {
        HomeRoute()
    }
}