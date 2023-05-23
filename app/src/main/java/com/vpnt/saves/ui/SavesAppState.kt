package com.vpnt.saves.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.vpnt.saves.navigation.TopLevelDestination
import com.vpnt.saves.navigation.bankAccountsRoute
import com.vpnt.saves.navigation.cardsRoute
import com.vpnt.saves.navigation.homeRoute
import com.vpnt.saves.navigation.navigateToBankAccounts
import com.vpnt.saves.navigation.navigateToCards
import com.vpnt.saves.navigation.navigateToHome
import com.vpnt.saves.navigation.navigateToProfile
import com.vpnt.saves.navigation.profileRoute
import kotlinx.coroutines.flow.StateFlow

@Composable
fun rememberSavesAppState(
    navController: NavHostController = rememberNavController()
): SavesAppState {

    return remember(navController) {
        SavesAppState(
            navController = navController
        )
    }
}

class SavesAppState(
    val navController: NavHostController
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            homeRoute -> TopLevelDestination.HOME
            cardsRoute -> TopLevelDestination.CARDS
            bankAccountsRoute -> TopLevelDestination.BANK_ACCOUNTS
            profileRoute -> TopLevelDestination.PROFILE
            else -> null
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

//        when (topLevelDestination) {
//            TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
//            TopLevelDestination.CARDS -> navController.navigateToCards(topLevelNavOptions)
//            TopLevelDestination.BANK_ACCOUNTS -> navController.navigateToBankAccounts(topLevelNavOptions)
//            TopLevelDestination.PROFILE -> navController.navigateToProfile(topLevelNavOptions)
//        }
    }
}
