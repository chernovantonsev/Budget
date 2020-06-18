package ru.antonc.budget.data.entities

enum class TransactionType(val type: String) {
    INCOME("income"), EXPENSE("expense"), NOT_SET("not_set");

    companion object {
        fun fromValue(value: String): TransactionType {
            return values().find { transactionType -> transactionType.type == value } ?: NOT_SET
        }
    }
}