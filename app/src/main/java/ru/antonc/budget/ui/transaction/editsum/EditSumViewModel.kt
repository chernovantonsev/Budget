package ru.antonc.budget.ui.transaction.editsum

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.antonc.budget.data.entities.Transaction
import ru.antonc.budget.data.entities.common.EventContent
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import ru.antonc.budget.util.extenstions.default
import javax.inject.Inject

class EditSumViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val _navigateToCategoriesEvent = MutableLiveData<EventContent<Pair<String, String>>>()
    val navigateToCategoriesEvent: LiveData<EventContent<Pair<String, String>>> =
        _navigateToCategoriesEvent
    
    private val _numberString = MutableLiveData<String>().default("0")
    val numberString: LiveData<String> = _numberString

    fun setNumPadResult(numberString: String) {
        _numberString.value = numberString
    }

    fun goToCategories()  = viewModelScope.launch {
        transactionRepository.getTransactionById("")?.let { transaction ->
            numberString.value?.toDoubleOrNull()?.let {
                transaction.sum = it
                transactionRepository.saveTransaction(transaction)

                _navigateToCategoriesEvent.value =
                    EventContent(transaction.id to transaction.type.name)
            }
        }
    }

}