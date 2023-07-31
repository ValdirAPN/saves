package br.com.saves.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.saves.R
import br.com.saves.common.FirebaseAuthManager
import br.com.saves.ui.theme.SavesTheme

@Composable
fun HomeHeader(
    username: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(100f))
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp)
        ) {
            val names = username.split(" ")
            val firstLetter = names.first()[0]
            val secondLetter = names.last()[0]
            val initials = "$firstLetter$secondLetter"

            Text(text = initials, color = MaterialTheme.colorScheme.onSurface)
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.greetings),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(text = "$username!", color = MaterialTheme.colorScheme.onBackground)
        }
        IconButton(
            onClick = {
                FirebaseAuthManager.auth.signOut()
            },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.signout),
                contentDescription = stringResource(id = R.string.logout),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun HomeHeaderPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            HomeHeader(username = "Carlos")
        }
    }
}