package br.com.saves.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.saves.R
import br.com.saves.model.BankAccount
import br.com.saves.ui.composables.SavesButton
import br.com.saves.ui.composables.SavesTextField
import br.com.saves.ui.theme.SavesTheme
import br.com.saves.utils.MONETARY_NUMBER_MAX_LENGTH
import br.com.saves.utils.NumberVisualTransformation
import br.com.saves.utils.toCurrency
import java.util.UUID

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AccountsContainer(
    bankAccounts: List<BankAccount>,
    onClickAddNewAccount: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.accounts),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(id = R.string.see_more),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 2
        ) {
            bankAccounts.take(3).forEach { bankAccount ->
                AccountContainer(
                    bankAccount = bankAccount,
                    onClick = { /*TODO*/ },
                    icon = R.drawable.wallet,
                    modifier = Modifier.weight(1f)
                )
            }
            NewAccountButton(onClick = onClickAddNewAccount, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun NewAccountButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Unspecified,
            containerColor = MaterialTheme.colorScheme.surface
        ),
        contentPadding = PaddingValues(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pluscircle),
                contentDescription = null,
                modifier = Modifier
            )
            Column {
                Text(
                    text = stringResource(id = R.string.add_account),
                    modifier = Modifier.width(100.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview
@Composable
fun AccountsContainerPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                AccountsContainer(BankAccount.fakeList()) {}
            }
        }
    }
}

@Composable
fun AccountContainer(
    bankAccount: BankAccount,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Unspecified,
            containerColor = MaterialTheme.colorScheme.surface
        ),
        contentPadding = PaddingValues(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
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
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = bankAccount.balance.toCurrency(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview
@Composable
fun AccountContainerPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            AccountContainer(
                bankAccount = BankAccount("", "Carteira", 0.0),
                onClick = {},
                modifier = Modifier,
                icon = R.drawable.wallet
            )
        }
    }
}

@Composable
fun NewAccountForm(
    onDismissRequest: () -> Unit,
    onCreateBankAccount: (BankAccount) -> Unit
) {

    var name by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.add_account),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(24.dp))
            SavesTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text(text = stringResource(id = R.string.account_name)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(16.dp))
            SavesTextField(
                value = balance,
                onValueChange = {
                    if (it.length <= MONETARY_NUMBER_MAX_LENGTH) {
                        balance = it
                    }
                },
                visualTransformation = NumberVisualTransformation(),
                placeholder = { Text(text = stringResource(id = R.string.balance)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(24.dp))
            SavesButton(
                onClick = {
                    val bankAccount = BankAccount(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        balance = balance.toDouble() / 100,
                    )
                    onCreateBankAccount(bankAccount)
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isNewAccountFormValid(name, balance)
            ) {
                Text(text = stringResource(id = R.string.add))
            }
        }
    }
}

private fun isNewAccountFormValid(name: String, balance: String): Boolean {
    return name.trim().isNotBlank()
            && balance.trim().isNotBlank()
            && balance.toDouble() > 0
}
