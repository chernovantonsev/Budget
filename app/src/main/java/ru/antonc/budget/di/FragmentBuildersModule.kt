package ru.antonc.budget.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.antonc.budget.ui.overview.OverviewFragment


@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeOverviewFragment(): OverviewFragment
}
