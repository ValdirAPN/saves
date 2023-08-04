package br.com.saves.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.saves.R
import br.com.saves.ui.theme.SavesTheme

@Composable
fun Action(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    label: String,
    containerColor: Color = MaterialTheme.colorScheme.primary
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.background,
            containerColor = containerColor
        ),
        contentPadding = PaddingValues(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = null)
            Text(
                text = label,
                modifier = Modifier.width(100.dp),
                style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 18.sp)
            )
        }
    }
}

@Preview
@Composable
fun ActionPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Action(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                icon = R.drawable.currency_circle_dollar,
                label = stringResource(id = R.string.register_income),
                containerColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}