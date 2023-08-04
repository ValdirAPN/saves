package br.com.saves.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.saves.R
import br.com.saves.ui.theme.SavesTheme

@Composable
fun SavesTopBar(
    title: String,
    showBackButton: Boolean = false,
    onBackPressed: (() -> Unit)? = null
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.heightIn(56.dp).padding(horizontal = 8.dp, vertical = 16.dp)) {
        if (showBackButton) {
            FilledIconButton(
                onClick = { onBackPressed?.invoke() },
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
private fun SavesTopBarPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            SavesTopBar(title = "Título", showBackButton = true)
        }
    }
}