package ru.antonc.budget.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.antonc.budget.data.dao.AccountDAO
import ru.antonc.budget.data.dao.CategoryDAO
import ru.antonc.budget.data.dao.TransactionDAO
import ru.antonc.budget.data.entities.Account
import ru.antonc.budget.data.entities.Category
import ru.antonc.budget.data.entities.Transaction

@Database(
    version = 1,
    entities = [
        Transaction::class,
        Category::class,
        Account::class
    ],
    exportSchema = false
)

@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDAO(): TransactionDAO
    abstract fun categoryDAO(): CategoryDAO
    abstract fun accountDAO(): AccountDAO


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