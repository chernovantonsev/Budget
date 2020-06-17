package ru.antonc.budget.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.antonc.budget.data.entities.Test


@Dao
abstract class TestDAO : BaseDAO<Test> {

    @Query("SELECT * from ${Test.TABLE_NAME}")
    abstract fun getAll(): LiveData<List<Test>>

    @Query("DELETE FROM ${Test.TABLE_NAME}")
    abstract fun clearTable()

}