package br.com.saves.feature.transaction

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val incomeNavigationRoute = "income_route"
const val expenseNavigationRoute = "expense_route"

fun NavController.navigateToIncome(navOptions: NavOptions? = null) {
    this.navigate(incomeNavigationRoute, navOptions)
}

fun NavGraphBuilder.incomeScreen(onBackPressed: () -> Unit) {
    composable(
        route = incomeNavigationRoute,
    ) {
        IncomeRoute(
            onBackPressed = onBackPressed,
        )
    }
}

fun NavController.navigateToExpense(navOptions: NavOptions? = null) {
    this.navigate(expenseNavigationRoute, navOptions)
}

fun NavGraphBuilder.expenseScreen(onBackPressed: () -> Unit) {
    composable(
        route = expenseNavigationRoute,
    ) {
        ExpenseRoute(
            onBackPressed = onBackPressed,
        )
    }
}
