package ru.antonc.budget.ui.transactions

import androidx.lifecycle.LiveData
import ru.antonc.budget.data.entities.Transaction
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class TransactionsListViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    val transactionsList: LiveData<List<Transaction>> = transactionRepository.getAllTransactions()
}