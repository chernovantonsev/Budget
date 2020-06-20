package ru.antonc.budget.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.antonc.budget.data.entities.Account
import ru.antonc.budget.data.entities.Transaction


@Dao
abstract class AccountDAO : BaseDAO<Account> {

    @Query("SELECT * from ${Account.TABLE_NAME}")
    abstract fun getAll(): LiveData<List<Account>>

    @Query("DELETE FROM ${Account.TABLE_NAME}")
    abstract fun clearTable()

}