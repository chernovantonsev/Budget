package ru.antonc.budget.ui.transaction

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
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

    private val transactionId = BehaviorRelay.create<String>()
    private var transactionType: TransactionType = TransactionType.NOT_SET

    private val _transaction = BehaviorRelay.create<FullTransaction>()
    val transaction: LiveData<FullTransaction> =
        LiveDataReactiveStreams.fromPublisher(_transaction.toFlowable(BackpressureStrategy.LATEST))

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
        transactionRepository.deleteEmptyTransaction()

        transactionId.toFlowable(BackpressureStrategy.LATEST)
            .flatMap { id ->
                transactionRepository.getOrCreateTransaction(id, transactionType)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _transaction.accept(it) }
            .addTo(dataCompositeDisposable)
    }

    fun setTransactionDetails(id: String = "", type: String = "") {
        transactionType = TransactionType.fromValue(type)
        transactionId.accept(id)
    }

    fun goToCategories(view: View) {
        saveTransaction()

        transaction.value?.let { transaction ->
            _navigateToCategoriesEvent.value =
                EventContent(transaction.info.id to transaction.info.type.type)

        }
    }

    fun saveTransaction(isFullSave: Boolean = false) {
        transaction.value?.let { transaction ->
            if (isFullSave && transaction.info.id.isEmpty())
                transaction.info.id = UUID.randomUUID().toString()
            transactionRepository.saveTransaction(transaction.info)
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
        _transaction.value?.let { transaction ->
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

    fun removeTransaction() {
        transaction.value?.let { transaction ->
            transactionId.accept("")
            transactionRepository.deleteTransaction(transaction.info)
        }
    }
}