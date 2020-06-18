package ru.antonc.budget.ui.overview

import android.view.View
import androidx.navigation.findNavController
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class OverviewViewModel @Inject constructor() : BaseViewModel() {



    fun addIncome(view: View) {
        view.findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToTransactionFragment(-1, TransactionType.INCOME.type))
    }

    fun addExpense(view: View) {
        view.findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToTransactionFragment(-1, TransactionType.EXPENSE.type))
    }

}