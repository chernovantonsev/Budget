package ru.antonc.budget.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.antonc.budget.ui.accounts.AccountsFragment
import ru.antonc.budget.ui.accounts.detail.AccountFragment
import ru.antonc.budget.ui.accounts.list.AccountsListFragment
import ru.antonc.budget.ui.accounts.summary.SummaryAccountsFragment
import ru.antonc.budget.ui.menu.MenuFragment
import ru.antonc.budget.ui.menu.categories.CustomizationCategoriesFragment
import ru.antonc.budget.ui.menu.categories.list.CategoriesListFragment
import ru.antonc.budget.ui.menu.settings.PreferencesFragment
import ru.antonc.budget.ui.menu.settings.SettingsFragment
import ru.antonc.budget.ui.overview.OverviewFragment
import ru.antonc.budget.ui.statistics.StatisticsFragment
import ru.antonc.budget.ui.statistics.daterange.DateRangeFragment
import ru.antonc.budget.ui.statistics.daterange.any.AnyRangeFragment
import ru.antonc.budget.ui.statistics.daterange.day.DayRangeFragment
import ru.antonc.budget.ui.statistics.piechart.PieChartStatisticsFragment
import ru.antonc.budget.ui.statistics.summary.SummaryStatisticsFragment
import ru.antonc.budget.ui.transaction.TransactionFragment
import ru.antonc.budget.ui.transaction.categories.CategoriesFragment
import ru.antonc.budget.ui.transaction.editsum.EditSumFragment
import ru.antonc.budget.ui.transaction.selectaccount.SelectAccountFragment
import ru.antonc.budget.ui.transaction.selecttype.TransactionTypeFragment
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
    abstract fun contributeAccountsListFragment(): AccountsListFragment

    @ContributesAndroidInjector
    abstract fun contributeAccountFragment(): AccountFragment

    @ContributesAndroidInjector
    abstract fun contributeStatisticsFragment(): StatisticsFragment

    @ContributesAndroidInjector
    abstract fun contributePreferencesFragment(): PreferencesFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributeMenuFragment(): MenuFragment

    @ContributesAndroidInjector
    abstract fun contributePieChartStatisticsFragment(): PieChartStatisticsFragment

    @ContributesAndroidInjector
    abstract fun contributeSummaryStatisticsFragment(): SummaryStatisticsFragment

    @ContributesAndroidInjector
    abstract fun contributeAccountsFragment(): AccountsFragment

    @ContributesAndroidInjector
    abstract fun contributeSummaryAccountsFragment(): SummaryAccountsFragment

    @ContributesAndroidInjector
    abstract fun contributeDateRangeFragment(): DateRangeFragment

    @ContributesAndroidInjector
    abstract fun contributeDayRangeFragment(): DayRangeFragment

    @ContributesAndroidInjector
    abstract fun contributeAnyRangeFragment(): AnyRangeFragment

    @ContributesAndroidInjector
    abstract fun contributeCustomizationCategoriesFragment(): CustomizationCategoriesFragment

    @ContributesAndroidInjector
    abstract fun contributeCategoriesListFragment(): CategoriesListFragment

    @ContributesAndroidInjector
    abstract fun contributeTransactionTypeFragment(): TransactionTypeFragment

    @ContributesAndroidInjector
    abstract fun contributeSelectAccountFragment(): SelectAccountFragment

    @ContributesAndroidInjector
    abstract fun contributeEditSumFragment(): EditSumFragment
}
