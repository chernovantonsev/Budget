package ru.antonc.budget.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Test.TABLE_NAME)
data class Test (@PrimaryKey val id: String) {

    companion object {
        const val TABLE_NAME = "test"
    }
}