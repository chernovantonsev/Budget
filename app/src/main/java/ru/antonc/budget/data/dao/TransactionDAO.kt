package ru.antonc.budget.data.dao

import androidx.room.*
import io.reactivex.Flowable
import ru.antonc.budget.data.entities.FullTransaction
import ru.antonc.budget.data.entities.Transaction


@Dao
interface TransactionDAO : BaseDAO<Transaction> {

    @androidx.room.Transaction
    @Query("SELECT * from ${Transaction.TABLE_NAME}  WHERE id <> ''")
    fun getAll(): Flowable<List<FullTransaction>>

    @Query("SELECT * from ${Transaction.TABLE_NAME} WHERE id = :transactionId LIMIT 1")
    suspend fun getTransactionById(transactionId: String): Transaction

    @androidx.room.Transaction
    @Query("SELECT * from ${Transaction.TABLE_NAME} WHERE id = :transactionId LIMIT 1")
    suspend fun getFullTransactionById(transactionId: String): FullTransaction?

    @Query("SELECT * from ${Transaction.TABLE_NAME} WHERE accountId = :accountId")
    suspend fun getTransactionByAccountId(accountId: Long): List<Transaction>

    @Query("DELETE FROM ${Transaction.TABLE_NAME} WHERE id = :transactionId")
    suspend fun deleteTransaction(transactionId: String = "")

    @Query("DELETE FROM ${Transaction.TABLE_NAME}")
    fun clearTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<Transaction>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuspend(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)
}