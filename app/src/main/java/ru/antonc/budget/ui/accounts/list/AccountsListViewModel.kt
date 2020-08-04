package ru.antonc.budget.ui.accounts.list

import androidx.lifecycle.LiveData
import ru.antonc.budget.data.entities.Account
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class AccountsListViewModel @Inject constructor(
    repository: TransactionRepository
) : BaseViewModel() {

    val accountsList: LiveData<List<Account>> = repository.getAllAccounts()
}