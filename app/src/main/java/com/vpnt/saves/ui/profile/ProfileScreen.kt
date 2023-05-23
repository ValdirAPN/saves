package com.vpnt.saves.ui.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vpnt.saves.ui.designsystem.components.SavesCard
import com.vpnt.saves.ui.designsystem.components.SavesClickableCard
import com.vpnt.saves.ui.designsystem.components.SavesText
import com.vpnt.saves.ui.designsystem.icon.SavesIcons

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
) {
    val menuItems = listOf(
        MenuItem(
            text = "Get in touch",
            icon = SavesIcons.Messages,
            onClick = {}
        ),
        MenuItem(
            text = "Logout",
            icon = SavesIcons.Logout,
            onClick = { }
        )
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Header()
        Spacer(modifier = Modifier.size(16.dp))
        Menu(menuItems = menuItems)
        Spacer(modifier = Modifier.size(16.dp))
        SavesText(text = "Version 1.0.0")
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}

@Composable
fun Header() {
    Text(text = "Username")
}
@Composable
fun Menu(menuItems: List<MenuItem>) {
    LazyColumn {
        items(
            items = menuItems
        ) { menuItem ->
            Spacer(modifier = Modifier.size(8.dp))
            MenuItemContainer(menuItem = menuItem)
        }
    }
}

@Composable
fun MenuItemContainer(menuItem: MenuItem) {
    SavesClickableCard(onClick = { menuItem.onClick() }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Icon(painter = painterResource(id = menuItem.icon), contentDescription = null)
            SavesText(text = menuItem.text)
        }
    }
}

data class MenuItem(
    val text: String,
    @DrawableRes val icon: Int,
    val onClick: () -> Unit
)