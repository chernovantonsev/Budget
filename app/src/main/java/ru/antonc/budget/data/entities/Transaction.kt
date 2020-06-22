package ru.antonc.budget.data.entities

import androidx.room.Entity
import androidx.room.Ignore
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = Transaction.TABLE_NAME, primaryKeys = ["id"])
data class Transaction(
    var id: String = "",
    val type: TransactionType,
    var date: Long,
    var sum: Double = 0.0,
    var comment: String = "",
    var categoryId: Long = -1L,
    var accountId: Long = -1L
) {


    @Ignore
    var category: Category = Category()

    @Ignore
    var account: Account = Account()


    companion object {
        const val TABLE_NAME = "transaction_table"
    }

    fun getFormattedDate(): String {
        return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)
    }

}
