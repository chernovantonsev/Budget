package ru.antonc.budget.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.antonc.budget.data.entities.Category
import ru.antonc.budget.data.entities.Transaction


@Dao
abstract class CategoryDAO : BaseDAO<Category> {

    @Query("SELECT * from ${Category.TABLE_NAME}")
    abstract fun getAll(): LiveData<List<Category>>

    @Query("DELETE FROM ${Category.TABLE_NAME}")
    abstract fun clearTable()

}