package com.vpnt.saves.ui.authentication.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vpnt.saves.ui.AuthState
import com.vpnt.saves.ui.AuthViewModel
import com.vpnt.saves.ui.designsystem.components.SavesText

private const val TAG = "LoginRoute"

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navigateToHome: () -> Unit
) {
    LoginScreen(
        modifier = modifier,
        authViewModel = authViewModel,
        navigateToHome = navigateToHome
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navigateToHome: () -> Unit,
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by authViewModel.authState.collectAsStateWithLifecycle()

    when (authState) {
        is AuthState.Loading -> {}
        is AuthState.Authenticated -> {
            navigateToHome()
        }

        is AuthState.Unauthenticated -> {
            Column(
                modifier = modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                SavesText(text = "Entrar", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.size(16.dp))
                SavesText(text = "E-mail", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.size(4.dp))
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(8.dp))
                SavesText(text = "Senha", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.size(4.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        authViewModel.login(username, password)
                    }
                ) {
                    Text(text = "Entrar")
                }
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Não possui uma conta?")
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        modifier = Modifier.clickable { },
                        text = "Criar conta.",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}