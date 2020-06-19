package ru.antonc.budget.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.antonc.budget.data.entities.Transaction


@Dao
interface TransactionDAO : BaseDAO<Transaction> {

    @Query("SELECT * from ${Transaction.TABLE_NAME}")
    fun getAll(): LiveData<List<Transaction>>

    @Query("SELECT EXISTS(SELECT * from ${Transaction.TABLE_NAME} WHERE id = :transactionId LIMIT 1)")
    fun isExistTransaction(transactionId: String): LiveData<Boolean>

    @Query("SELECT * from ${Transaction.TABLE_NAME} WHERE id = :transactionId LIMIT 1")
    fun getTransactionById(transactionId: String): LiveData<Transaction?>

    @Query("DELETE FROM ${Transaction.TABLE_NAME}")
    fun clearTable()
}