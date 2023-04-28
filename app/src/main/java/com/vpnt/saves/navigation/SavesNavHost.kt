package com.vpnt.saves.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.vpnt.saves.ui.SavesAppState

@Composable
fun SavesNavHost(
    appState: SavesAppState,
    modifier: Modifier = Modifier,
    startDestination: String = homeRoute
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen()
        cardsScreen()
        bankAccountsScreen()
        profileScreen()
    }
}