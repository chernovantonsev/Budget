package ru.antonc.budget.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.antonc.budget.ui.accounts.AccountsFragment
import ru.antonc.budget.ui.accounts.create.AccountFragment
import ru.antonc.budget.ui.categories.CategoriesFragment
import ru.antonc.budget.ui.menu.MenuFragment
import ru.antonc.budget.ui.overview.OverviewFragment
import ru.antonc.budget.ui.statistics.StatisticsFragment
import ru.antonc.budget.ui.transaction.TransactionFragment
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

    @ContributesAndroidInjector
    abstract fun contributeCategoriesFragment(): CategoriesFragment

    @ContributesAndroidInjector
    abstract fun contributeAccountsFragment(): AccountsFragment

    @ContributesAndroidInjector
    abstract fun contributeAccountFragment(): AccountFragment

    @ContributesAndroidInjector
    abstract fun contributeStatisticsFragment(): StatisticsFragment

    @ContributesAndroidInjector
    abstract fun contributeMenuFragment(): MenuFragment
}
