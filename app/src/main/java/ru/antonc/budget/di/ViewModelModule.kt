package ru.antonc.budget.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.antonc.budget.ui.accounts.AccountsViewModel
import ru.antonc.budget.ui.accounts.detail.AccountViewModel
import ru.antonc.budget.ui.categories.CategoriesViewModel
import ru.antonc.budget.ui.menu.MenuViewModel
import ru.antonc.budget.ui.transaction.TransactionViewModel
import ru.antonc.budget.ui.overview.OverviewViewModel
import ru.antonc.budget.ui.settings.SettingsFragment
import ru.antonc.budget.ui.settings.SettingsViewModel
import ru.antonc.budget.ui.statistics.StatisticsViewModel
import ru.antonc.budget.ui.statistics.piechart.PieChartStatisticsViewModel
import ru.antonc.budget.ui.statistics.summary.SummaryStatisticsViewModel
import ru.antonc.budget.ui.transactions.TransactionsListViewModel


@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(OverviewViewModel::class)
    abstract fun bindOverviewViewModel(overviewViewModel: OverviewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionViewModel::class)
    abstract fun bindTransactionViewModel(transactionViewModel: TransactionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionsListViewModel::class)
    abstract fun bindTransactionsListViewModel(transactionsListViewModel: TransactionsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoriesViewModel(categoriesViewModel: CategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountsViewModel::class)
    abstract fun bindAccountsViewModel(accountsViewModel: AccountsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(accountViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StatisticsViewModel::class)
    abstract fun bindStatisticsViewModel(statisticsViewModel: StatisticsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    abstract fun bindMenuViewModel(menuViewModel: MenuViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PieChartStatisticsViewModel::class)
    abstract fun bindPieChartStatisticsViewModel(pieChartStatisticsViewModel: PieChartStatisticsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SummaryStatisticsViewModel::class)
    abstract fun bindSummaryStatisticsViewModel(summaryStatisticsViewModel: SummaryStatisticsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
