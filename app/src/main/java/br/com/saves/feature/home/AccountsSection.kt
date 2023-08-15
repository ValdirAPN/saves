package br.com.saves.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import br.com.saves.model.Bank
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
                    icon = bankAccount.bank.icon,
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
                    .size(32.dp)
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
                bankAccount = BankAccount("", Bank.NUBANK, "Carteira", 0.0),
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

    var bank by remember { mutableStateOf(Bank.WALLET) }
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
            val banks = Bank.values().asList()
            BankIconDropdown(
                items = banks,
                selectedItem = Bank.valueOf(bank.name),
                onItemSelected = { _, item ->
                    bank = item
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
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
                        bank = bank,
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

@Preview
@Composable
fun NewAccountForm() {
    SavesTheme {
        NewAccountForm(onDismissRequest = {}, onCreateBankAccount = {})
    }
}

@Composable
fun BankIconDropdown(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    items: List<Bank>,
    selectedItem: Bank? = null,
    onItemSelected: (index: Int, item: Bank) -> Unit,
    drawItem: @Composable (Bank, Boolean, () -> Unit) -> Unit = { item, itemEnabled, onClick ->
        val title = if (item.title == "default") {
            stringResource(id = R.string.wallet)
        } else {
            item.title
        }
        BankIconDropdownItem(
            text = title,
            icon = painterResource(id = item.icon),
            enabled = itemEnabled,
            onClick = onClick
        )
    }
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.height(IntrinsicSize.Min), contentAlignment = Alignment.CenterStart) {
        SavesTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            trailingIcon = {
                Icon(Icons.Filled.ArrowDropDown, "")
            },
            readOnly = true
        )

        Row(
            modifier = Modifier.padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .fillMaxSize(),
                painter = painterResource(id = selectedItem?.icon ?: R.drawable.wallet),
                contentDescription = null,
                tint = Color.Unspecified
            )
            var title = selectedItem?.title
            if (title == null || title == "default") title = stringResource(id = R.string.wallet)
            Text(text = title)
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable(enabled = enabled) { expanded = true },
            color = Color.Transparent,
        ) {}
    }

    if (expanded) {
        Dialog(
            onDismissRequest = { expanded = false }
        ) {
            Surface(
                modifier = Modifier.padding(vertical = 16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                val listState = rememberLazyListState()
                if (selectedItem.toString().isEmpty().not()) {
                    val index = items.indexOf(selectedItem)

                    if (index > -1) {
                        LaunchedEffect(key1 = "ScrollToSelected") {
                            listState.scrollToItem(index = index)
                        }
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                    itemsIndexed(items) { index, item ->
                        drawItem(
                            item,
                            true
                        ) {
                            onItemSelected(index, item)
                            expanded = false
                        }

                        if (index < items.lastIndex) {
                            Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun BankIconDropdownItem(
    text: String,
    icon: Painter,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(enabled) { onClick() }
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(modifier = Modifier.size(24.dp), painter = icon, contentDescription = null, tint = Color.Unspecified)
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

private fun isNewAccountFormValid(name: String, balance: String): Boolean {
    return name.trim().isNotBlank()
            && balance.trim().isNotBlank()
            && balance.toDouble() > 0
}
