package com.vpnt.saves.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.vpnt.saves.ui.cards.CardsScreen

const val cardsRoute = "cards_route"

fun NavController.navigateToCards(navOptions: NavOptions? = null) {
    this.navigate(cardsRoute, navOptions)
}

fun NavGraphBuilder.cardsScreen() {
    composable(route = cardsRoute) {
        CardsScreen()
    }
}