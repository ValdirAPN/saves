package br.com.saves.feature.home

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
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
import br.com.saves.model.CreditCard
import br.com.saves.model.CreditCardIssuer
import br.com.saves.ui.composables.SavesButton
import br.com.saves.ui.composables.SavesTextField
import br.com.saves.ui.theme.SavesTheme
import br.com.saves.utils.MONETARY_NUMBER_MAX_LENGTH
import br.com.saves.utils.NumberVisualTransformation
import br.com.saves.utils.toCurrency
import java.util.UUID

@Composable
fun CardsContainer(
    creditCards: List<CreditCard>,
    onClickAddNewCard: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.cards),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            NewCardButton(onClick = onClickAddNewCard)
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            if (creditCards.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_credit_cards_found),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            creditCards.forEach { creditCard ->
                CardContainer(
                    creditCard = creditCard,
                    onClick = { /*TODO*/ },
                )
            }
        }
    }
}

@Composable
fun NewCardButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedIconButton(
        onClick = onClick,
        modifier = modifier.size(32.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f))
    ) {
        Icon(
            modifier = Modifier.padding(8.dp),
            painter = painterResource(id = R.drawable.plus),
            contentDescription = stringResource(id = R.string.add_card),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun CardsContainerPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CardsContainer(CreditCard.fakeList()) {}
        }
    }
}

@Preview
@Composable
fun CardsContainerEmptyPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CardsContainer(emptyList()) {}
        }
    }
}

@Composable
fun CardContainer(
    creditCard: CreditCard,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val iconTint = if (creditCard.issuer.foreground != null) {
            Color(creditCard.issuer.foreground)
        } else Color.Unspecified

        Icon(
            painter = painterResource(id = creditCard.issuer.icon),
            tint = iconTint,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(100f))
                .background(Color(creditCard.issuer.background))
                .padding(8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Text(
                text = creditCard.name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = stringResource(
                    id = R.string.due_day,
                    creditCard.dueDay.toString().padStart(2, '0')
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = stringResource(id = R.string.available_limit),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = creditCard.availableLimit.toCurrency(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview
@Composable
fun CardContainerPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CardContainer(
                creditCard = CreditCard(
                    id = "",
                    issuer = CreditCardIssuer.NUBANK,
                    name = "Nubank",
                    limit = 110.0,
                    availableLimit = 10.0,
                    2
                ),
                onClick = {},
                modifier = Modifier,
            )
        }
    }
}

@Composable
fun NewCardForm(
    onDismissRequest: () -> Unit,
    onCreateCard: (CreditCard) -> Unit
) {

    var issuer by remember { mutableStateOf(CreditCardIssuer.DEFAULT) }
    var name by remember { mutableStateOf("") }
    var limit by remember { mutableStateOf("") }
    var dueDay by remember { mutableStateOf("") }

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
                text = stringResource(id = R.string.add_card),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(24.dp))
            val creditCardIssuers = CreditCardIssuer.values().asList()
            CreditCardIssuerIconDropdown(
                items = creditCardIssuers,
                selectedItem = CreditCardIssuer.valueOf(issuer.name),
                onItemSelected = { _, item ->
                    issuer = item
                }
            )
            Spacer(modifier = Modifier.size(24.dp))
            SavesTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text(stringResource(id = R.string.card_name)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(16.dp))
            SavesTextField(
                value = limit,
                onValueChange = {
                    if (it.length <= MONETARY_NUMBER_MAX_LENGTH) {
                        limit = it
                    }
                },
                visualTransformation = NumberVisualTransformation(),
                placeholder = { Text(stringResource(id = R.string.limit)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(16.dp))
            SavesTextField(
                value = dueDay,
                onValueChange = { dueDay = it },
                placeholder = { Text(stringResource(id = R.string.due_day_placeholder)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(24.dp))
            SavesButton(
                onClick = {
                    val card = CreditCard(
                        id = UUID.randomUUID().toString(),
                        issuer = issuer,
                        name = name,
                        limit = limit.toDouble() / 100,
                        availableLimit = limit.toDouble() / 100,
                        dueDay = dueDay.toInt()
                    )
                    onCreateCard(card)
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isNewCreditCardFormValid(name, limit, dueDay)
            ) {
                Text(text = stringResource(id = R.string.add))
            }
        }
    }
}

@Preview
@Composable
fun NewCardFormPreview() {
    SavesTheme {
        NewCardForm({}, {})
    }
}

@Composable
fun CreditCardIssuerIconDropdown(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    items: List<CreditCardIssuer>,
    selectedItem: CreditCardIssuer? = null,
    onItemSelected: (index: Int, item: CreditCardIssuer) -> Unit,
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
        CreditCardIssuerIconDropdownDialog(
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
fun CreditCardIssuerIconDropdownDialog(
    onDismissRequest: () -> Unit,
    items: List<CreditCardIssuer>,
    onClick: (index: Int, item: CreditCardIssuer) -> Unit
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
fun CreditCardIssuerIconDropdownDialogPreview() {
    SavesTheme {
        CreditCardIssuerIconDropdownDialog(
            onDismissRequest = {},
            items = CreditCardIssuer.values().asList(),
            onClick = { _, _ -> }
        )
    }
}

@Composable
fun CreditCardIssuerIconDropdownItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    background: Color,
    foreground: Color,
    onClick: () -> Unit,
) {
    FilledIconButton(
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = background
        )
    ) {
        Icon(
            modifier = Modifier.width(24.dp),
            painter = icon,
            contentDescription = null,
            tint = foreground
        )
    }
}

fun isNewCreditCardFormValid(name: String, limit: String, dueDay: String): Boolean {
    return name.trim().isNotBlank()
            && limit.trim().isNotBlank()
            && limit.toDouble() > 0
            && dueDay.trim().isNotBlank()
            && dueDay.toDouble() > 0
            && dueDay.toDouble() <= 31
}
