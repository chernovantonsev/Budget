package ru.antonc.budget.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.antonc.budget.data.AppDatabase
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
    fun provideDb(app: Application): AppDatabase {
        return AppDatabase.getInstance(app)
    }
}
