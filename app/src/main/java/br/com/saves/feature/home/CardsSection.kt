package br.com.saves.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.saves.R
import br.com.saves.model.Account
import br.com.saves.model.Card
import br.com.saves.ui.theme.SavesTheme
import br.com.saves.utils.toCurrency

@Composable
fun CardsContainer(cards: List<Card>) {
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
                    CardContainer(card = cards[i], onClick = { /*TODO*/ }, icon = R.drawable.wallet, modifier = Modifier.weight(1f))
                    if ((i + 1) < cards.size) {
                        CardContainer(card = cards[i+1], onClick = { /*TODO*/ }, icon = R.drawable.wallet, modifier = Modifier.weight(1f))
                    } else {
                        NewCardButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f))
                    }
                }
            }

            if (cards.size % 2 == 0) {
                Row {
                    NewCardButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f))
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
                    text = stringResource(id = R.string.btn_add_card_label),
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
                CardsContainer(Card.fakeList())
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
