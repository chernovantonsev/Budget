package ru.antonc.budget.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import ru.antonc.budget.data.entities.Transaction


@Dao
interface TransactionDAO : BaseDAO<Transaction> {

    @Query("SELECT * from ${Transaction.TABLE_NAME} WHERE isNew = 0")
    fun getAll(): Flowable<List<Transaction>>

    @Query("SELECT * from ${Transaction.TABLE_NAME} WHERE id = :transactionId LIMIT 1")
    fun getTransactionById(transactionId: String): Flowable<List<Transaction>>

    @Query("DELETE FROM ${Transaction.TABLE_NAME}")
    fun clearTable()
}