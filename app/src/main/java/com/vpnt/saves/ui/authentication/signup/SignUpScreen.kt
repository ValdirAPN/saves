package com.vpnt.saves.ui.authentication.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vpnt.saves.ui.designsystem.components.SavesText

@Composable
fun SignUpRoute(
    modifier: Modifier = Modifier
) {
    SignUpScreen(modifier = modifier)
}

@Composable
fun SignUpScreen(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        SavesText(text = "Criar conta", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.size(16.dp))
        SavesText(text = "Nome", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.size(4.dp))
        OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.size(8.dp))
        SavesText(text = "E-mail", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.size(4.dp))
        OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.size(8.dp))
        SavesText(text = "Senha", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.size(4.dp))
        OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.size(8.dp))
        SavesText(text = "Confirmar senha", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.size(4.dp))
        OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Criar conta")
        }
    }
}