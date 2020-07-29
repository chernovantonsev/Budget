package ru.antonc.budget.ui.transaction.selectaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.antonc.budget.data.entities.Account
import ru.antonc.budget.data.entities.FullTransaction
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.data.entities.common.Event
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class SelectAccountViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val _navigateToEditSumEvent = MutableLiveData<Event>()
    val navigateToEditSumEvent: LiveData<Event> =
        _navigateToEditSumEvent

    var transaction: FullTransaction? = null

    val accountsList: LiveData<List<Account>> = transactionRepository.getAllAccounts()

    init {
        viewModelScope.launch {
            transactionRepository.deleteEmptyTransaction()
        }
    }

    fun selectAccount(account: Account) = viewModelScope.launch {
        transaction?.let { transaction ->
            transaction.info.accountId = account.id
            transactionRepository.saveTransaction(transaction.info)

            _navigateToEditSumEvent.value = Event()
        }
    }

    fun setTransactionInfo(transactionTypeName: String) {
        viewModelScope.launch {
            TransactionType.fromValue(transactionTypeName)?.let { transactionType ->
                transaction = transactionRepository.getOrCreateTransaction(transactionType = transactionType)
            }
        }
    }

}