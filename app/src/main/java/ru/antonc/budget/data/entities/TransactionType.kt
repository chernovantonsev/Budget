package ru.antonc.budget.data.entities

import ru.antonc.budget.R

enum class TransactionType(val type: String, val icon: Int) {
    INCOME("income", R.drawable.ic_add_circle_outline),
    EXPENSE("expense", R.drawable.ic_remove_circle_outline),
    NOT_SET("not_set", R.drawable.ic_close);

    companion object {
        fun fromValue(value: String): TransactionType {
            return values().find { transactionType -> transactionType.type == value } ?: NOT_SET
        }
    }
}