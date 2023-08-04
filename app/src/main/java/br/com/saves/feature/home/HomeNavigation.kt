package br.com.saves.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val homeNavigationRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToIncome: () -> Unit,
    navigateToExpense: () -> Unit,
) {
    composable(
        route = homeNavigationRoute,
    ) {
        HomeRoute(
            navigateToIncome = navigateToIncome,
            navigateToExpense = navigateToExpense,
        )
    }
}