package ru.antonc.budget.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDAO<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: T)

    @Delete
    fun delete(vararg obj: T): Int

    @Update
    fun update(vararg obj: T): Int

}