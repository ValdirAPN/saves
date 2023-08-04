package br.com.saves.feature.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.saves.R
import br.com.saves.ui.composables.SavesButton
import br.com.saves.ui.composables.SavesTextField
import br.com.saves.ui.composables.SavesTopBar
import br.com.saves.utils.NumberVisualTransformation

@Composable
fun IncomeRoute(onBackPressed: () -> Unit) {
    IncomeScreen(onBackPressed = onBackPressed)
}

@Composable
fun IncomeScreen(onBackPressed: () -> Unit) {

    var name by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var account by remember { mutableStateOf("") }
    var origin by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Column {
        SavesTopBar(
            title = stringResource(id = R.string.register_income),
            showBackButton = true,
            onBackPressed = onBackPressed
        )

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SavesTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text(stringResource(id = R.string.title)) })
                SavesTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = value,
                    onValueChange = {
                        if (it.length <= 16) {
                            value = it
                        }
                    },
                    visualTransformation = NumberVisualTransformation(),
                    placeholder = { Text(stringResource(id = R.string.value)) })
                SavesTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = account,
                    onValueChange = { account = it },
                    placeholder = { Text(stringResource(id = R.string.account)) })
                SavesTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = origin,
                    onValueChange = { origin = it },
                    placeholder = { Text(stringResource(id = R.string.origin)) })
                SavesTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = date,
                    onValueChange = { date = it },
                    placeholder = { Text(stringResource(id = R.string.date)) })
            }

            SavesButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /*TODO*/ }
            ) {
                Text(text = stringResource(id = R.string.register))
            }
        }

        Spacer(modifier = Modifier.size(24.dp))
    }
}
