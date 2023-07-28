package br.com.saves.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import br.com.saves.SavesAppState
import br.com.saves.feature.home.homeNavigationRoute
import br.com.saves.feature.home.homeScreen

@Composable
fun SavesNavHost(
    modifier: Modifier = Modifier,
    appState: SavesAppState,
    startDestination: String = homeNavigationRoute
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen()
    }
}