package ru.antonc.budget.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.antonc.budget.ui.accounts.detail.AccountViewModel
import ru.antonc.budget.ui.accounts.list.AccountsListViewModel
import ru.antonc.budget.ui.accounts.summary.SummaryAccountsViewModel
import ru.antonc.budget.ui.menu.MenuViewModel
import ru.antonc.budget.ui.menu.categories.CustomizationCategoriesViewModel
import ru.antonc.budget.ui.menu.categories.list.CategoriesListViewModel
import ru.antonc.budget.ui.overview.OverviewViewModel
import ru.antonc.budget.ui.statistics.StatisticsViewModel
import ru.antonc.budget.ui.statistics.daterange.DateRangeViewModel
import ru.antonc.budget.ui.statistics.daterange.any.AnyRangeViewModel
import ru.antonc.budget.ui.statistics.daterange.day.DayRangeViewModel
import ru.antonc.budget.ui.statistics.piechart.PieChartStatisticsViewModel
import ru.antonc.budget.ui.statistics.summary.SummaryStatisticsViewModel
import ru.antonc.budget.ui.transaction.TransactionViewModel
import ru.antonc.budget.ui.transaction.categories.CategoriesViewModel
import ru.antonc.budget.ui.transaction.editsum.EditSumViewModel
import ru.antonc.budget.ui.transaction.selectaccount.SelectAccountViewModel
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
    @ViewModelKey(AccountsListViewModel::class)
    abstract fun bindAccountsViewModel(accountsListViewModel: AccountsListViewModel): ViewModel

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
    @ViewModelKey(PieChartStatisticsViewModel::class)
    abstract fun bindPieChartStatisticsViewModel(pieChartStatisticsViewModel: PieChartStatisticsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SummaryStatisticsViewModel::class)
    abstract fun bindSummaryStatisticsViewModel(summaryStatisticsViewModel: SummaryStatisticsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SummaryAccountsViewModel::class)
    abstract fun bindSummaryAccountsViewModel(summaryAccountsViewModel: SummaryAccountsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DateRangeViewModel::class)
    abstract fun bindDateRangeViewModel(DateRangeViewModel: DateRangeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DayRangeViewModel::class)
    abstract fun bindDayRangeViewModel(dayRangeViewModel: DayRangeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AnyRangeViewModel::class)
    abstract fun bindAnyRangeViewModel(anyRangeViewModel: AnyRangeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CustomizationCategoriesViewModel::class)
    abstract fun bindCustomizationCategoriesViewModel(customizationCategoriesViewModel: CustomizationCategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesListViewModel::class)
    abstract fun bindCategoriesListViewModel(categoriesListViewModel: CategoriesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectAccountViewModel::class)
    abstract fun bindSelectAccountViewModel(selectAccountViewModel: SelectAccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditSumViewModel::class)
    abstract fun bindEditSumViewModel(editSumViewModel: EditSumViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
