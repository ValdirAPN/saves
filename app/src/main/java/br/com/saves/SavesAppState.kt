package br.com.saves

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.saves.data.util.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberSavesAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): SavesAppState {
    return remember(
        coroutineScope,
        navController,
        networkMonitor
    ) {
        SavesAppState(
            coroutineScope = coroutineScope,
            navController = navController,
            networkMonitor = networkMonitor
        )
    }
}

@Stable
class SavesAppState(
    val coroutineScope: CoroutineScope,
    val navController: NavHostController,
    networkMonitor: NetworkMonitor
) {
    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )
}