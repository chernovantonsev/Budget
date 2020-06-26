package ru.antonc.budget.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.antonc.budget.ui.main.MainActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
