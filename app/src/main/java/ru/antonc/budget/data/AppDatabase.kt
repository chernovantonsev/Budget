package ru.antonc.budget.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.antonc.budget.data.dao.TestDAO
import ru.antonc.budget.data.entities.Test

@Database(
    version = 1,
    entities = [
        Test::class
    ],
    exportSchema = false
)


abstract class AppDatabase : RoomDatabase() {

    abstract fun testDAO(): TestDAO

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context, AppDatabase::class.java,
                context.packageName
            )
                .fallbackToDestructiveMigration()
                .build()
        }


    }
}