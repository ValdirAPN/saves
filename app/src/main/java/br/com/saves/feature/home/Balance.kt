package br.com.saves.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.saves.R
import br.com.saves.ui.theme.SavesTheme
import br.com.saves.utils.toCurrency

@Composable
fun BalanceOverview(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = Color.Unspecified
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(stringResource(id = R.string.your_balance), color = MaterialTheme.colorScheme.onSurface)
            Text(628.79.toCurrency(), style = MaterialTheme.typography.headlineLarge)
        }
    }
}

@Preview
@Composable
fun BalanceOverviewPreview() {
    SavesTheme {
        Surface {
            Surface(
                color = MaterialTheme.colorScheme.background
            ) {
                BalanceOverview()
            }
        }
    }
}