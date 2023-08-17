package br.com.saves.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
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
import br.com.saves.model.Bank
import br.com.saves.model.BankAccount
import br.com.saves.ui.composables.SavesButton
import br.com.saves.ui.composables.SavesTextField
import br.com.saves.ui.theme.SavesTheme
import br.com.saves.utils.MONETARY_NUMBER_MAX_LENGTH
import br.com.saves.utils.NumberVisualTransformation
import br.com.saves.utils.toCurrency
import java.util.UUID

@Composable
fun AccountsContainer(
    bankAccounts: List<BankAccount>,
    onClickAddNewAccount: () -> Unit,
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
            NewAccountButton(onClick = onClickAddNewAccount)
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            bankAccounts.forEach { bankAccount ->
                AccountContainer(
                    bankAccount = bankAccount,
                    onClick = { /*TODO*/ },
                    icon = bankAccount.bank.icon,
                )
            }
        }
    }
}

@Composable
fun NewAccountButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedIconButton(
        onClick = onClick,
        modifier = modifier.size(32.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f))
    ) {
        Icon(
            modifier = Modifier.padding(8.dp),
            painter = painterResource(id = R.drawable.plus),
            contentDescription = stringResource(id = R.string.add_account),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun AccountsContainerPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                AccountsContainer(BankAccount.fakeList(), {})
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
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val iconTint = if (bankAccount.bank.foreground != null) {
            Color(bankAccount.bank.foreground)
        } else Color.Unspecified

        Icon(
            painter = painterResource(id = icon),
            tint = iconTint,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(100f))
                .background(Color(bankAccount.bank.background))
                .padding(8.dp)
        )
        val name =
            if (bankAccount.name == "default") stringResource(id = R.string.wallet)
            else bankAccount.name

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = stringResource(id = R.string.balance),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = bankAccount.balance.toCurrency(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
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

    var bank by remember { mutableStateOf(Bank.DEFAULT) }
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
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = modifier
                .width(80.dp)
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            selectedItem?.let { item ->
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(100.dp))
                        .background(Color(item.background))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp),
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        tint = if (item.foreground != null) Color(item.foreground) else Color.Unspecified
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(100.dp))
                    .clickable(enabled = enabled) { expanded = true },
                color = Color.Transparent,
            ) {}
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = stringResource(id = R.string.select_the_icon), color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.widthIn(max = 100.dp))
    }

    if (expanded) {
        BankIconDropdownDialog(
            onDismissRequest = { expanded = false },
            items,
            onClick = { index, item ->
                onItemSelected(index, item)
                expanded = false
            }
        )
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BankIconDropdownDialog(
    onDismissRequest: () -> Unit,
    items: List<Bank>,
    onClick: (index: Int, item: Bank) -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(12.dp)
        ) {
            val itemsPerRow = 4
            val rows = items.chunked(itemsPerRow)
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                maxItemsInEachRow = 4
            ) {
                rows.forEachIndexed { rowIndex, creditCardIssuers ->
                    val isLastRow = rowIndex == rows.size - 1
                    creditCardIssuers.forEachIndexed { index, item ->
                        CreditCardIssuerIconDropdownItem(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            icon = painterResource(id = item.icon),
                            background = Color(item.background),
                            foreground = if (item.foreground != null) Color(item.foreground) else Color.Unspecified,
                            onClick = { onClick(index, item) }
                        )
                    }
                    if (isLastRow) {
                        val spacers = itemsPerRow - creditCardIssuers.size
                        for (i in 0..spacers) {
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(0.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun BankIconDropdownDialogPreview() {
    BankIconDropdownDialog(
        onDismissRequest = {},
        items = Bank.values().asList(),
        onClick = { _, _ ->}
    )
}
@Preview
@Composable
fun BankIconDropdownPreview() {
    BankIconDropdown(
        items = Bank.values().asList(),
        onItemSelected = { _, _, ->}
    )
}

@Composable
fun BankIconDropdownItem(
    icon: Painter,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier.padding(16.dp).background(MaterialTheme.colorScheme.surface),
        onClick = onClick
    ) {
        Icon(modifier = Modifier.size(24.dp), painter = icon, contentDescription = null, tint = Color.Unspecified)
    }
}

private fun isNewAccountFormValid(name: String, balance: String): Boolean {
    return name.trim().isNotBlank()
            && balance.trim().isNotBlank()
            && balance.toDouble() > 0
}
