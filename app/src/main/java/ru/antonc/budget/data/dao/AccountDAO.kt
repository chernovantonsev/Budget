package ru.antonc.budget.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import ru.antonc.budget.data.entities.Account
import ru.antonc.budget.data.entities.Transaction


@Dao
interface AccountDAO : BaseDAO<Account> {

    @Query("SELECT * from ${Account.TABLE_NAME}")
    fun getAll(): LiveData<List<Account>>

    @Query("SELECT * from ${Account.TABLE_NAME}")
    fun getAllAccounts(): Flowable<List<Account>>

    @Query("SELECT * from ${Account.TABLE_NAME} WHERE id = :id LIMIT 1")
    fun getAccountById(id: Long): Flowable<Account>

    @Query("DELETE FROM ${Account.TABLE_NAME}")
    fun clearTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(accounts: List<Account>)
}