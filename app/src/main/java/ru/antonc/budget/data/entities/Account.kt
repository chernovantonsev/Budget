package ru.antonc.budget.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.antonc.budget.R

@Entity(tableName = Account.TABLE_NAME)
data class Account(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    var name: String = "",

    var balance: Double = 0.0,

    var icon: Int = R.drawable.ic_money
) {

    companion object {
        const val TABLE_NAME = "account_table"
    }
}