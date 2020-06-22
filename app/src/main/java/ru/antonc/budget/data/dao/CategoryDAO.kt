package ru.antonc.budget.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import ru.antonc.budget.data.entities.Category


@Dao
interface CategoryDAO : BaseDAO<Category> {

    @Query("SELECT * from ${Category.TABLE_NAME}")
    fun getAll(): LiveData<List<Category>>

    @Query("SELECT * from ${Category.TABLE_NAME} WHERE id = :id LIMIT 1")
    fun getCategoryById(id: Long): Flowable<Category>

    @Query("DELETE FROM ${Category.TABLE_NAME}")
    fun clearTable()

}