package ru.antonc.budget.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import ru.antonc.budget.data.entities.Account


@Dao
interface AccountDAO : BaseDAO<Account> {

    @Query("SELECT * from ${Account.TABLE_NAME}")
    fun getAll(): LiveData<List<Account>>

    @Query("SELECT * from ${Account.TABLE_NAME} WHERE id = :id LIMIT 1")
    fun getAccountById(id: Long): Flowable<List<Account>>

    @Query("DELETE FROM ${Account.TABLE_NAME}")
    fun clearTable()

}