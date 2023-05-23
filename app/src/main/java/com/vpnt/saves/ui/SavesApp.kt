package com.vpnt.saves.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.vpnt.saves.navigation.SavesNavHost
import com.vpnt.saves.navigation.TopLevelDestination
import com.vpnt.saves.navigation.navigateToAuthenticationGraph
import com.vpnt.saves.ui.designsystem.components.SavesNavigationBar
import com.vpnt.saves.ui.designsystem.components.SavesNavigationBarItem

private const val TAG = "SavesApp"

@Composable
fun SavesApp(
    appState: SavesAppState = rememberSavesAppState(),
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    var isBottomNavigationVisible by remember { mutableStateOf(false) }

    LaunchedEffect(authState) {
        Log.d(TAG, "LaunchedEffect: authState = $authState")
        when (authState) {
            is AuthState.Unauthenticated -> {
                isBottomNavigationVisible = false
                appState.navController.navigateToAuthenticationGraph()
            }

            is AuthState.Authenticated -> {
                isBottomNavigationVisible = true
            }

            else -> {
                isBottomNavigationVisible = false
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (isBottomNavigationVisible) {
                SavesBottomBar(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination
                )
            }
        }
    ) { padding ->
        Row(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            Column(Modifier.fillMaxSize()) {
                SavesNavHost(
                    appState = appState,
                    authViewModel = authViewModel
                )
            }
        }
    }
}

@Composable
fun SavesBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    SavesNavigationBar(
        modifier = modifier
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            SavesNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.unselectedIcon),
                        contentDescription = null
                    )
                },
                selectedIcon = {
                    Icon(
                        painter = painterResource(id = destination.selectedIcon),
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
