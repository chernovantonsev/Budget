package ru.antonc.budget.ui.transaction

import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.antonc.budget.data.entities.Transaction
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val transactionId = MutableLiveData<Int>()
    private lateinit var transactionType: TransactionType

    val transaction: LiveData<Transaction> = transactionId.switchMap { id ->
        if (id > 0)
            transactionRepository.getTransactionById(id)
        else createTransaction()
    }

    val selectedCategoryName: LiveData<String> = transaction.map { transaction ->
        transaction.category.name
    }

    val date = transaction.switchMap {
        liveData { emit(timeStampToTime(it.date)) }
    }

    fun setTransactionDetails(id: Int = -1, type: String = "") {
        if (id < 0)
            transactionType = TransactionType.fromValue(type)
        transactionId.value = id
    }

    fun goToCategories(view: View) {

    }

    private fun createTransaction(): LiveData<Transaction> = MutableLiveData(
        Transaction(
            type = transactionType,
            date = Calendar.getInstance().timeInMillis
        )
    )


    fun saveTransaction(transaction: Transaction? = null) {
        (transaction ?: this.transaction.value)?.let { transactionToSave ->
            viewModelScope.launch {
                transactionRepository.createTransaction(transactionToSave)
                transactionId.postValue(transactionToSave.id)
            }
        }
    }

    private suspend fun timeStampToTime(timestamp: Long): String {
        val date = Date(timestamp)
        return date.toString()
    }

    fun setSum(sumString: String) {
        (sumString.toDoubleOrNull() ?: 0.0).let {
            transaction.value?.sum = it
        }
    }
}