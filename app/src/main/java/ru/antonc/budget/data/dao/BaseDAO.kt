package ru.antonc.budget.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDAO<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg obj: T)

    @Delete
    suspend fun delete(vararg obj: T)

    @Update
    suspend fun update(vararg obj: T)

}