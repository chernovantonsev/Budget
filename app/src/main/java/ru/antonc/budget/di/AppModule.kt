package ru.antonc.budget.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.antonc.budget.data.AppDatabase
import ru.antonc.budget.data.dao.CategoryDAO
import ru.antonc.budget.data.dao.TransactionDAO
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return AppDatabase.getInstance(app)
    }

    @Singleton
    @Provides
    fun provideTransactionDAO(database: AppDatabase): TransactionDAO {
        return database.transactionDAO()
    }

    @Singleton
    @Provides
    fun provideCategoryDAO(database: AppDatabase): CategoryDAO {
        return database.categoryDAO()
    }
}
