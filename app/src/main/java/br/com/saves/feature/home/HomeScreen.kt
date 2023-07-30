package br.com.saves.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.saves.common.FirebaseAuthManager
import br.com.saves.ui.composables.SavesButton
import br.com.saves.ui.theme.SavesTheme

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState: HomeUiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState
) {
    when (uiState) {
        HomeUiState.Loading -> {
            Text(text = "Loading")
        }
        is HomeUiState.Success -> {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.size(16.dp))
                HomeHeader(username = uiState.userData.name)
                SavesButton(
                    onClick = { FirebaseAuthManager.auth.signOut() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sair")
                }
            }
        }
    }
}

@Composable
fun HomeHeader(
    username: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(100f))
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
        ) {
            Text(text = "VA", color = MaterialTheme.colorScheme.onSurface)
        }
        Column {
            Text(text = "Bem vindo(a),")
            Text(text = "$username!")
        }
    }
}

@Preview
@Composable
fun HomeHeaderPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            HomeHeader(username = "Carlos")
        }
    }
}
