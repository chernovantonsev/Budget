package ru.antonc.budget.ui.overview

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.navigation.findNavController
import ru.antonc.budget.data.entities.Account
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject
import kotlin.math.min

class OverviewViewModel @Inject constructor(
    repository: TransactionRepository
) : BaseViewModel() {

    val accountsList: LiveData<List<Account>> = repository.getAllAccounts()
        .map { accountsList -> accountsList.subList(0, min(accountsList.size, 4)) }

    fun addIncome(view: View) {
        view.findNavController().navigate(
            OverviewFragmentDirections.actionOverviewFragmentToTransactionFragment(
                "",
                TransactionType.INCOME.type
            )
        )
    }

    fun addExpense(view: View) {
        view.findNavController().navigate(
            OverviewFragmentDirections.actionOverviewFragmentToTransactionFragment(
                "",
                TransactionType.EXPENSE.type
            )
        )
    }

    fun toAccountsList(view: View) {
        view.findNavController().navigate(
            OverviewFragmentDirections.actionOverviewFragmentToAccountsFragment()
        )
    }

}