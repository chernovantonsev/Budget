package ru.antonc.budget.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import ru.antonc.budget.data.entities.FullTransaction
import ru.antonc.budget.data.entities.Transaction


@Dao
interface TransactionDAO : BaseDAO<Transaction> {

    @Query("SELECT * from ${Transaction.TABLE_NAME}")
    fun getAll(): Flowable<List<FullTransaction>>

    @Query("SELECT * from ${Transaction.TABLE_NAME} WHERE id = :transactionId LIMIT 1")
    fun getTransactionById(transactionId: String): Flowable<List<FullTransaction>>

    @Query("DELETE FROM ${Transaction.TABLE_NAME} WHERE id = :transactionId")
    fun deleteTransaction(transactionId: String = "") : Completable

    @Query("DELETE FROM ${Transaction.TABLE_NAME}")
    fun clearTable()



}