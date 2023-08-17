package br.com.saves.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import br.com.saves.SavesAppState
import br.com.saves.feature.authentication.authenticationNavigationRoute
import br.com.saves.feature.authentication.authenticationScreen
import br.com.saves.feature.bankaccounts.bankAccountsScreen
import br.com.saves.feature.bankaccounts.navigateToBankAccounts
import br.com.saves.feature.home.homeScreen
import br.com.saves.feature.transaction.expenseScreen
import br.com.saves.feature.transaction.incomeScreen
import br.com.saves.feature.transaction.navigateToExpense
import br.com.saves.feature.transaction.navigateToIncome
import br.com.saves.feature.transaction.navigateToTransactions
import br.com.saves.feature.transaction.transactionsScreen

@Composable
fun SavesNavHost(
    modifier: Modifier = Modifier,
    appState: SavesAppState,
    startDestination: String = authenticationNavigationRoute
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        authenticationScreen()
        homeScreen(
            navigateToIncome = { navController.navigateToIncome() },
            navigateToExpense = { navController.navigateToExpense() },
            navigateToTransactions = { navController.navigateToTransactions() },
        )
        incomeScreen(
            onBackPressed = { navController.popBackStack() }
        )
        expenseScreen(
            onBackPressed = { navController.popBackStack() }
        )
        transactionsScreen(
            onBackPressed = { navController.popBackStack() }
        )
        bankAccountsScreen(
            onBackPressed = { navController.popBackStack() }
        )
    }
}