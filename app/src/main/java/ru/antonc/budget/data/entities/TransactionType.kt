package ru.antonc.budget.data.entities

import ru.antonc.budget.R

enum class TransactionType(val type: String, val icon: Int) {
    INCOME("income", R.drawable.ic_income),
    EXPENSE("expense", R.drawable.ic_expense),
    NOT_SET("not_set", R.drawable.ic_close);

    companion object {
        fun fromValue(value: String): TransactionType {
            return values().find { transactionType -> transactionType.type == value } ?: NOT_SET
        }
    }
}