package ru.antonc.budget.ui.transaction

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.antonc.budget.data.entities.Account
import ru.antonc.budget.data.entities.FullTransaction
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.data.entities.common.EventContent
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import ru.antonc.budget.util.extenstions.combineWith
import java.util.*
import javax.inject.Inject

class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val transactionInfo = MutableLiveData<Pair<String, TransactionType?>>()

    val transaction: LiveData<FullTransaction> =
        transactionInfo.switchMap { (id, transactionType) ->
            liveData {
                emit(transactionRepository.getOrCreateTransaction(id, transactionType))
            }
        }

    val accounts: LiveData<List<Account>> = transactionRepository.getAllAccounts()

    val selectedAccountPosition: LiveData<Int> =
        transaction.combineWith(accounts) { transaction, accounts ->
            if (transaction != null && accounts != null) {
                accounts.forEachIndexed { index, account ->
                    if (account == transaction.account)
                        return@combineWith index
                }
            }

            return@combineWith 0
        }

    private val _datePickerEvent = MutableLiveData<EventContent<Long>>()
    val datePickerEvent: LiveData<EventContent<Long>> = _datePickerEvent

    private val _navigateToCategoriesEvent = MutableLiveData<EventContent<Pair<String, String>>>()
    val navigateToCategoriesEvent: LiveData<EventContent<Pair<String, String>>> =
        _navigateToCategoriesEvent

    init {
        viewModelScope.launch {
            transactionRepository.deleteEmptyTransaction()
        }
    }

    fun setTransactionDetails(id: String = "", transactionTypeName: String = "") {
        transactionInfo.value = Pair(id, TransactionType.fromValue(transactionTypeName))
    }

    fun goToCategories(view: View) {
        saveTransaction()

        transaction.value?.let { transaction ->
            _navigateToCategoriesEvent.value =
                EventContent(transaction.info.id to transaction.info.type.name)

        }
    }

    fun saveTransaction(isFullSave: Boolean = false) = viewModelScope.launch {
        transaction.value?.let { transaction ->
            if (isFullSave && transaction.info.id.isEmpty())
                transaction.info.id = UUID.randomUUID().toString()
            transactionRepository.saveTransaction(transaction.info, isFullSave)
        }
    }

    fun setSum(sumString: String) {
        (sumString.toDoubleOrNull() ?: 0.0).let {
            transaction.value?.let { transaction ->
                transaction.info.sum = it
            }
        }
    }

    fun onDateClick() {
        transaction.value?.let { transaction ->
            _datePickerEvent.value = EventContent(transaction.info.date)
        }
    }

    fun setDate(newDate: Long) {
        transaction.value?.let { transaction ->
            transaction.info.date = newDate
        }
    }

    fun onAccountSelected(
        adapterView: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        adapterView?.selectedItem?.let { selectedAccount ->
            if (selectedAccount is Account)
                transaction.value?.let { transaction ->
                    transaction.info.accountId = selectedAccount.id
                    transaction.account = selectedAccount
                }
        }
    }

    fun removeTransaction() = viewModelScope.launch {
        transaction.value?.let { transaction ->
            transactionInfo.value = null
            transactionRepository.deleteTransaction(transaction.info)
        }
    }
}