package com.vpnt.saves.ui.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vpnt.saves.data.model.CreditCard
import com.vpnt.saves.extensions.toCurrency
import com.vpnt.saves.ui.designsystem.components.SavesButton
import com.vpnt.saves.ui.designsystem.components.SavesCard
import com.vpnt.saves.ui.designsystem.components.SavesText
import com.vpnt.saves.ui.designsystem.icon.SavesIcons
import com.vpnt.saves.ui.designsystem.theme.SavesTheme

@Composable
fun CardsRoute(
    modifier: Modifier = Modifier,
    viewModel: CardsViewModel = hiltViewModel()
) {

    val cardsUiState: CardsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    CardsScreen(
        uiState = cardsUiState,
        modifier = modifier
    )
}

@Composable
fun CardsScreen(
    uiState: CardsUiState,
    modifier: Modifier = Modifier
) {
    var showCardBottomSheet by remember {
        mutableStateOf(false)
    }

    fun onClickNewCard() {
        showCardBottomSheet = true
    }

    Column(
        modifier = modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        when(uiState) {
            is CardsUiState.Success -> {
                Header(onClickNewCard = { onClickNewCard() })
                Spacer(modifier = Modifier.size(16.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = CreditCard.fakeList(),
                        key = { creditCard -> creditCard.id }
                    ) { creditCard ->
                        CardContainer(creditCard = creditCard)
                    }
                }

                if (showCardBottomSheet) {
                    NewCardBottomSheet(onDismissRequest = { showCardBottomSheet = false })
                }
            }
            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCardBottomSheet(
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        sheetState = SheetState(skipPartiallyExpanded = true),
        onDismissRequest = { onDismissRequest() }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
        ) {
            SavesText(text = "Novo cartão", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.size(16.dp))
            SavesText(text = "Título", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.size(4.dp))
            OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(16.dp))
            SavesText(text = "Quantidade", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.size(4.dp))
            OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(16.dp))
            SavesText(text = "Data", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.size(4.dp))
            OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(16.dp))

            SavesButton(
                text = "Adicionar",
                modifier = Modifier.fillMaxWidth(),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
fun CardsScreenPreview() {
    SavesTheme {
        CardsScreen(
            uiState = CardsUiState.Success(
                cards = CreditCard.fakeList()
            )
        )
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    onClickNewCard: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        SavesText(text = "Cartões", style = MaterialTheme.typography.titleLarge)

        Button(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            onClick = { onClickNewCard() }
        ) {
            Icon(painter = painterResource(id = SavesIcons.Plus), contentDescription = "")
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Novo cartão")
        }
    }
}

@Composable
fun CardContainer(creditCard: CreditCard) {
    SavesCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row {
                SavesText(text = creditCard.name, style = MaterialTheme.typography.labelLarge)
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    SavesText(text = "Available limit")
                    SavesText(
                        text = creditCard.availableLimit.toCurrency(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Column {
                    SavesText(text = "Invoice")
                    SavesText(
                        text = creditCard.invoice.toCurrency(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}
