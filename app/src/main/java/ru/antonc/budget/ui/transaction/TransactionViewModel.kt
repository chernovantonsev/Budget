package ru.antonc.budget.ui.transaction

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import ru.antonc.budget.data.entities.Transaction
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.data.entities.common.EventContent
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val transactionId = BehaviorRelay.create<String>()
    private var transactionType: TransactionType = TransactionType.NOT_SET

    private val _transaction = MutableLiveData<Transaction>()
    val transaction: LiveData<Transaction> = _transaction

    private val _datePickerEvent = MutableLiveData<EventContent<Long>>()
    val datePickerEvent: LiveData<EventContent<Long>> = _datePickerEvent

    init {
        transactionId.toFlowable(BackpressureStrategy.LATEST)
            .flatMap { id -> transactionRepository.getTransaction(id, transactionType) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _transaction.value = it }
            .addTo(dataCompositeDisposable)
    }

    fun setTransactionDetails(id: String = "", type: String = "") {
        transactionType = TransactionType.fromValue(type)
        transactionId.accept(if (id.isEmpty()) UUID.randomUUID().toString() else id)
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
            transactionToSave.isNew = false
            transactionRepository.saveTransaction(transactionToSave)
        }
    }

    fun setSum(sumString: String) {
        (sumString.toDoubleOrNull() ?: 0.0).let {
            transaction.value?.sum = it
        }
    }

    fun onDateClick() {
        _transaction.value?.let { transaction ->
            _datePickerEvent.value = EventContent(transaction.date)
        }
    }

    fun setDate(newDate: Long) {
        transaction.value?.let { transaction ->
            transaction.date = newDate
            _transaction.value = transaction
        }
    }
}