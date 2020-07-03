package ru.antonc.budget.ui.transaction.selectaccount

import androidx.lifecycle.LiveData
import ru.antonc.budget.data.entities.Account
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class SelectAccountViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    val accountsList: LiveData<List<Account>> = transactionRepository.getAllAccounts()


    init {

    }


}