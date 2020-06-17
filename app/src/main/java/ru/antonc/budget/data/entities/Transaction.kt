package ru.antonc.budget.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Transaction.TABLE_NAME)
data class Transaction (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val type: TransactionType,
    var date: Long,
    var categoryId: Int,
    var sum: Double,
    var comment: String
) {

    companion object {
        const val TABLE_NAME = "transaction_table"
    }
}