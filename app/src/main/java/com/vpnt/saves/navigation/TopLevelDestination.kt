package com.vpnt.saves.navigation

import androidx.annotation.DrawableRes
import com.vpnt.saves.R
import com.vpnt.saves.ui.designsystem.icon.SavesIcons

enum class TopLevelDestination(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val iconTextId: Int,
) {
    HOME(
        selectedIcon = SavesIcons.Home,
        unselectedIcon = SavesIcons.Home,
        iconTextId = R.string.home
    ),
    CARDS(
        selectedIcon = SavesIcons.Card,
        unselectedIcon = SavesIcons.Card,
        iconTextId = R.string.cards
    ),
    BANK_ACCOUNTS(
        selectedIcon = SavesIcons.BankAccounts,
        unselectedIcon = SavesIcons.BankAccounts,
        iconTextId = R.string.bank_accounts
    ),
    PROFILE(
        selectedIcon = SavesIcons.Profile,
        unselectedIcon = SavesIcons.Profile,
        iconTextId = R.string.profile
    )
}