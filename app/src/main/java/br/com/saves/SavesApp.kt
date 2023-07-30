package br.com.saves

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import br.com.saves.data.util.NetworkMonitor
import br.com.saves.feature.authentication.authenticationNavigationRoute
import br.com.saves.feature.home.homeNavigationRoute
import br.com.saves.navigation.SavesNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavesApp(
    isAuthenticated: Boolean,
    networkMonitor: NetworkMonitor,
    appState: SavesAppState = rememberSavesAppState(networkMonitor = networkMonitor)
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    val notConnectedMessage = stringResource(id = R.string.not_connected)
    LaunchedEffect(isOffline) {
        Log.d("TAG", "$isOffline")
        if (isOffline) {
            snackBarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { padding ->
        Row(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(Modifier.fillMaxSize()) {
                SavesNavHost(
                    appState = appState,
                    startDestination = if (isAuthenticated) homeNavigationRoute else authenticationNavigationRoute
                )
            }
        }
    }
}