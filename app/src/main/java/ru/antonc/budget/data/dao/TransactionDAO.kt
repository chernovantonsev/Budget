package ru.antonc.budget.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.antonc.budget.data.entities.Transaction


@Dao
abstract class TransactionDAO : BaseDAO<Transaction> {

    @Query("SELECT * from ${Transaction.TABLE_NAME}")
    abstract fun getAll(): LiveData<List<Transaction>>

    @Query("DELETE FROM ${Transaction.TABLE_NAME}")
    abstract fun clearTable()

}