package com.vpnt.saves.ui.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@Composable
fun SavesCard(
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ),
    content:  @Composable() (ColumnScope.() -> Unit)
) {
    Card(
        modifier = modifier.clip(MaterialTheme.shapes.large),
        colors = colors,
        content = content
    )
}

@Composable
fun SavesClickableCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colors: CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ),
    content:  @Composable() (ColumnScope.() -> Unit)
) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .clickable { onClick() },
        colors = colors,
        content = content
    )
}