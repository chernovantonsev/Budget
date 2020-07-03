package ru.antonc.budget.ui.transaction.editsum

import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class EditSumViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {


}