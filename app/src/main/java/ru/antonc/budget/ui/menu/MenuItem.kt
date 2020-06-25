package ru.antonc.budget.ui.menu

import ru.antonc.budget.R

enum class MenuItem(val title: Int, val icon: Int) {
    CATEGORIES(R.string.title_categories, R.drawable.ic_category),
    ACCOUNTS(R.string.title_accounts, R.drawable.ic_account_wallet),
    SETTINGS(R.string.title_settings, R.drawable.ic_settings)
}