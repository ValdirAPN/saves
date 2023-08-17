package br.com.saves.feature.bankaccounts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.saves.R
import br.com.saves.model.BankAccount
import br.com.saves.ui.composables.SavesTopBar
import br.com.saves.utils.toCurrency

@Composable
fun BankAccountsRoute(
    onBackPressed: () -> Unit,
    viewModel: BankAccountsViewModel = hiltViewModel()
) {
    val uiState: BankAccountsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    BankAccountsScreen(
        onBackPressed = onBackPressed,
        uiState = uiState
    )
}

@Composable
fun BankAccountsScreen(
    onBackPressed: () -> Unit,
    uiState: BankAccountsUiState
) {
    when(uiState) {
        BankAccountsUiState.Loading -> {}
        is BankAccountsUiState.Success -> {
            Column {
                SavesTopBar(
                    title = stringResource(id = R.string.accounts),
                    showBackButton = true,
                    onBackPressed = onBackPressed
                )

                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = uiState.bankAccounts,
                        key = { item -> item.id }
                    ) { bankAccount ->
                        BankAccountItem(bankAccount = bankAccount) {}
                    }
                }
            }
        }
    }
}

@Composable
fun BankAccountItem(
    modifier: Modifier = Modifier,
    bankAccount: BankAccount,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Unspecified,
            containerColor = MaterialTheme.colorScheme.surface
        ),
        contentPadding = PaddingValues(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                painter = painterResource(id = bankAccount.bank.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(100f))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val name =
                    if (bankAccount.name == "default") stringResource(id = R.string.wallet)
                    else bankAccount.name

                Text(
                    text = name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = bankAccount.balance.toCurrency(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
