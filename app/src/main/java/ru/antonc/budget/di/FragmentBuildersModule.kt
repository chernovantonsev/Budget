package ru.antonc.budget.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.antonc.budget.ui.transaction.TransactionFragment
import ru.antonc.budget.ui.overview.OverviewFragment
import ru.antonc.budget.ui.transactions.TransactionsListFragment


@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeOverviewFragment(): OverviewFragment

    @ContributesAndroidInjector
    abstract fun contributeTransactionFragment(): TransactionFragment

    @ContributesAndroidInjector
    abstract fun contributeTransactionListFragment(): TransactionsListFragment
}
