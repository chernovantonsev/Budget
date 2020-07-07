package ru.antonc.budget.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import ru.antonc.budget.data.entities.Category
import ru.antonc.budget.data.entities.TransactionType


@Dao
interface CategoryDAO : BaseDAO<Category> {

    @Query("SELECT * from ${Category.TABLE_NAME}")
    fun getAll(): Flowable<List<Category>>

    @Query("SELECT * from ${Category.TABLE_NAME} WHERE id = :id LIMIT 1")
    fun getCategoryById(id: Long): Flowable<Category>

    @Query("DELETE FROM ${Category.TABLE_NAME}")
    fun clearTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)

}