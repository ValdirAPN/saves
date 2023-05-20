package com.vpnt.saves.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.vpnt.saves.ui.bankaccounts.BankAccountsRoute

const val bankAccountsRoute = "bank_accounts_route"

fun NavController.navigateToBankAccounts(navOptions: NavOptions? = null) {
    this.navigate(bankAccountsRoute, navOptions)
}

fun NavGraphBuilder.bankAccountsScreen() {
    composable(route = bankAccountsRoute) {
        BankAccountsRoute()
    }
}