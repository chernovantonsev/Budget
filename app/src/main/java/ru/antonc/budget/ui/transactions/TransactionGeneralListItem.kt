package ru.antonc.budget.ui.transactions

import ru.antonc.budget.data.entities.FullTransaction
import ru.antonc.budget.ui.transactions.TransactionListItem.Companion.TYPE_DATE
import ru.antonc.budget.ui.transactions.TransactionListItem.Companion.TYPE_GENERAL

interface TransactionListItem {

    companion object {
        const val TYPE_DATE = 0
        const val TYPE_GENERAL = 1
    }

    fun getType(): Int
}

class TransactionGeneralListItem(val transaction: FullTransaction) : TransactionListItem {
    override fun getType() = TYPE_GENERAL
}

class TransactionDateListItem(var date: String = "", var sum: Double = 0.0) : TransactionListItem {
    override fun getType() = TYPE_DATE
}