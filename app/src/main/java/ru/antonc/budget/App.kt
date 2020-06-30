package ru.antonc.budget

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import ru.antonc.budget.di.AppInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector : DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)

        sharedPreferences.getString("themePref", ThemeHelper.DEFAULT_MODE)?.let { themePref ->
            ThemeHelper.applyTheme(themePref)
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}