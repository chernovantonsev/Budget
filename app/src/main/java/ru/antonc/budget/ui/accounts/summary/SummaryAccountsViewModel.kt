package ru.antonc.budget.ui.accounts.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class SummaryAccountsViewModel @Inject constructor(
    repository: TransactionRepository
) : BaseViewModel() {

    val sumOnAccounts: LiveData<Double> = repository.getAllAccounts()
        .map { accountsList -> accountsList.sumByDouble { account -> account.balance } }
}