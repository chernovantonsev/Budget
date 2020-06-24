package ru.antonc.budget.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Category.TABLE_NAME)
data class Category(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String,
    var type: TransactionType
) {

    companion object {
        const val TABLE_NAME = "category_table"
    }
}