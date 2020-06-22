package ru.antonc.budget.data.entities

import androidx.room.Embedded
import androidx.room.Relation

class FullTransaction {

    @Embedded
    lateinit var info: Transaction

    @Relation(parentColumn = "categoryId", entityColumn = "id")
    var category: Category? = null

    @Relation(parentColumn = "accountId", entityColumn = "id")
    var account: Account? = null
}