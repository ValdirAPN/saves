package br.com.saves.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.saves.R
import br.com.saves.model.Card
import br.com.saves.ui.composables.SavesButton
import br.com.saves.ui.composables.SavesTextField
import br.com.saves.ui.theme.SavesTheme
import br.com.saves.utils.toCurrency

@Composable
fun CardsContainer(
    cards: List<Card>,
    onClickAddNewCard: () -> Unit
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
            Text(
                text = stringResource(id = R.string.see_more),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in cards.indices step 2) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CardContainer(
                        card = cards[i],
                        onClick = { /*TODO*/ },
                        icon = R.drawable.wallet,
                        modifier = Modifier.weight(1f)
                    )
                    if ((i + 1) < cards.size) {
                        CardContainer(
                            card = cards[i + 1],
                            onClick = { /*TODO*/ },
                            icon = R.drawable.wallet,
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        NewCardButton(onClick = onClickAddNewCard, modifier = Modifier.weight(1f))
                    }
                }
            }

            if (cards.size % 2 == 0) {
                Row {
                    NewCardButton(onClick = onClickAddNewCard, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun NewCardButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
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
                    text = stringResource(id = R.string.add_card),
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
fun CardsContainerPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                CardsContainer(Card.fakeList(), {})
            }
        }
    }
}

@Composable
fun CardContainer(
    card: Card,
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
                Text(
                    text = card.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = card.limit.toCurrency(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
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
                card = Card("", "Nubank", 0.0),
                onClick = {},
                modifier = Modifier,
                icon = R.drawable.wallet
            )
        }
    }
}

@Composable
fun NewCardForm(
    onDismissRequest: () -> Unit,
    onCreateCard: (Card) -> Unit
) {

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
            SavesTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text(stringResource(id = R.string.account_name)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(16.dp))
            SavesTextField(
                value = limit,
                onValueChange = { limit = it },
                placeholder = { Text(stringResource(id = R.string.limit)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(16.dp))
            SavesTextField(
                value = dueDay,
                onValueChange = { dueDay = it },
                placeholder = { Text(stringResource(id = R.string.due_day)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(24.dp))
            SavesButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(id = R.string.add))
            }
        }
    }

//    ModalBottomSheet(
//        sheetState = SheetState(skipPartiallyExpanded = true),
//        onDismissRequest = { onDismissRequest() },
//        containerColor = MaterialTheme.colorScheme.background
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .verticalScroll(rememberScrollState())
//                .imePadding()
//                .imeNestedScroll()
//                .padding(horizontal = 16.dp)
//                .padding(bottom = 16.dp),
//        ) {
//            Text(
//                modifier = Modifier.fillMaxWidth(),
//                text = stringResource(id = R.string.add_card),
//                style = MaterialTheme.typography.bodyLarge,
//                color = MaterialTheme.colorScheme.onBackground,
//                textAlign = TextAlign.Center
//            )
//            Spacer(modifier = Modifier.size(24.dp))
//            SavesTextField(
//                value = name,
//                onValueChange = { name = it },
//                placeholder = { Text(stringResource(id = R.string.account_name)) },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.size(16.dp))
//            SavesTextField(
//                value = limit,
//                onValueChange = { limit = it },
//                placeholder = { Text(stringResource(id = R.string.limit)) },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.size(16.dp))
//            SavesTextField(
//                value = dueDay,
//                onValueChange = { dueDay = it },
//                placeholder = { Text(stringResource(id = R.string.due_day)) },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.size(24.dp))
//            SavesButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
//                Text(text = stringResource(id = R.string.add))
//            }
//            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
//        }
//    }
}
