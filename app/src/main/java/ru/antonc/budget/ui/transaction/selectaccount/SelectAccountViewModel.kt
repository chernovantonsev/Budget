package ru.antonc.budget.ui.transaction.selectaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
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

    private val transactionType = BehaviorRelay.create<TransactionType>()

    private val transaction = BehaviorRelay.create<FullTransaction>()

    val accountsList: LiveData<List<Account>> = transactionRepository.getAllAccounts()

    init {
        transactionRepository.deleteEmptyTransaction()

        transactionType.toFlowable(BackpressureStrategy.LATEST)
            .flatMap { transactionType ->
                transactionRepository.getOrCreateTransaction(transactionType = transactionType)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { transaction.accept(it) }
            .addTo(dataCompositeDisposable)
    }


    fun selectAccount(account: Account) {
        transaction.value?.let { transaction ->
            transaction.info.accountId = account.id
            transactionRepository.saveTransaction(transaction.info)

            _navigateToEditSumEvent.value = Event()
        }
    }

    fun setTransactionInfo(transactionTypeName: String) {
        TransactionType.fromValue(transactionTypeName)?.let { transactionType ->
            this.transactionType.accept(transactionType)
        }
    }

}