package com.vpnt.saves.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.vpnt.saves.ui.AuthViewModel
import com.vpnt.saves.ui.authentication.login.LoginRoute
import com.vpnt.saves.ui.authentication.signup.SignUpRoute

const val loginRoute = "login_route"
const val signUpRoute = "signup_route"

fun NavController.navigateToLogin() {
    if (this.currentDestination?.route != loginRoute) {
        this.navigate(loginRoute, navOptions {
            popUpTo(homeRoute) {
                inclusive = true
            }
        })
    }
}

fun NavGraphBuilder.loginScreen(
    authViewModel: AuthViewModel,
    navigateToHome: () -> Unit
) {
    composable(route = loginRoute) {
        LoginRoute(
            authViewModel = authViewModel,
            navigateToHome = navigateToHome
        )
    }
}

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate(signUpRoute, navOptions)
}

fun NavGraphBuilder.signUpScreen() {
    composable(route = signUpRoute) {
        SignUpRoute()
    }
}
