package com.vpnt.saves.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import com.vpnt.saves.ui.AuthViewModel
import com.vpnt.saves.ui.SavesAppState

const val mainGraph = "main_graph"
const val authenticationGraph = "authentication_graph"
@Composable
fun SavesNavHost(
    appState: SavesAppState,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    startDestination: String = mainGraph,
) {

    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        navigation(startDestination = homeRoute, route = mainGraph) {
            homeScreen()
            cardsScreen()
            bankAccountsScreen()
            profileScreen()
        }

        navigation(startDestination = loginRoute, route = authenticationGraph) {
            loginScreen(
                authViewModel = authViewModel,
                navigateToHome = {
                    navController.navigateToMainGraph()
                }
            )
            signUpScreen()
        }
    }
}

fun NavController.navigateToMainGraph() {
    this.navigate(mainGraph)
}

fun NavController.navigateToAuthenticationGraph() {
    this.navigate(authenticationGraph, navOptions {
        popUpTo(mainGraph) {
            inclusive = true
        }
    })
}
