package br.com.saves.feature.bankaccounts

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val bankAccountsRoute = "bank_accounts_route"

fun NavController.navigateToBankAccounts(navOptions: NavOptions? = null) {
    this.navigate(bankAccountsRoute, navOptions)
}

fun NavGraphBuilder.bankAccountsScreen(onBackPressed: () -> Unit) {
    composable(
        route = bankAccountsRoute
    ) {
        BankAccountsRoute(
            onBackPressed = onBackPressed
        )
    }
}
