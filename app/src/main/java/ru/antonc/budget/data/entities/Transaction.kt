package ru.antonc.budget.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = Transaction.TABLE_NAME)
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: TransactionType,

    var date: Long,

    @Embedded(prefix = "category_")
    var category: Category = Category(),

    var sum: Double = 0.0,

    var comment: String = ""
) {

    companion object {
        const val TABLE_NAME = "transaction_table"
    }

    fun getFormattedDate(): String {
        return SimpleDateFormat("dd.MM", Locale.getDefault()).format(date)
    }

}
