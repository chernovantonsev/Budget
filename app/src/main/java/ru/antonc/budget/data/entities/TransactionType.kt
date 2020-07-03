package ru.antonc.budget.data.entities

import ru.antonc.budget.R

enum class TransactionType(val title: String, val icon: Int) {
    INCOMES("Доходы", R.drawable.ic_income),
    EXPENSES("Расходы", R.drawable.ic_expense);

    companion object {
        fun fromValue(value: String): TransactionType? {
            return values().find { transactionType -> transactionType.name == value }
        }
    }
}