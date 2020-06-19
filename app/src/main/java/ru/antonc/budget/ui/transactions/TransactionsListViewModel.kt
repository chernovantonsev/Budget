package ru.antonc.budget.ui.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.antonc.budget.data.entities.Transaction
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class TransactionsListViewModel @Inject constructor(
    transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val _transactionsList =  MutableLiveData<List<Transaction>>()
    val transactionsList: LiveData<List<Transaction>> = _transactionsList


    init {
        transactionRepository.getAllTransactions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{_transactionsList.value = it}
            .addTo(dataCompositeDisposable)
    }

}