package br.com.saves.feature.authentication

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val authenticationNavigationRoute = "authentication_route"

fun NavController.navigateToAuthentication(navOptions: NavOptions? = null) {
    this.navigate(authenticationNavigationRoute, navOptions)
}

fun NavGraphBuilder.authenticationScreen() {
    composable(
        route = authenticationNavigationRoute
    ) {
        AuthenticationRoute()
    }
}
