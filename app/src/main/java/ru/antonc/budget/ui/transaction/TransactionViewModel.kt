package ru.antonc.budget.ui.transaction

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import kotlinx.coroutines.launch
import ru.antonc.budget.data.entities.Transaction
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val transactionId = MutableLiveData<String>()
    private lateinit var transactionType: TransactionType

    val transaction: LiveData<Transaction> = transactionId.switchMap { id ->
        transactionRepository.getTransaction(id, transactionType).also {
            it.value?.let { transaction ->
                if (transactionId.value != transaction.id)
                    transactionId.postValue(transaction.id)
            }
        }
    }

    fun setTransactionDetails(id: String = "", type: String = "") {
        if (id.isEmpty())
            transactionType = TransactionType.fromValue(type)
        transactionId.value = id
    }

    fun goToCategories(view: View) {
        transactionId.value?.let { id ->
            view.findNavController().navigate(
                TransactionFragmentDirections.actionTransactionFragmentToCategoriesFragment(id)
            )
        }
    }


    fun saveTransaction() {
        transaction.value?.let { transactionToSave ->
            viewModelScope.launch {
                transactionRepository.saveTransaction(transactionToSave)
            }
        }
    }

    fun setSum(sumString: String) {
        (sumString.toDoubleOrNull() ?: 0.0).let {
            transaction.value?.sum = it
        }
    }
}