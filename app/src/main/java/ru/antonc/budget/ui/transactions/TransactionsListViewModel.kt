package ru.antonc.budget.ui.transactions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.antonc.budget.data.entities.FullTransaction
import ru.antonc.budget.data.entities.Transaction
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class TransactionsListViewModel @Inject constructor(
    transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val _transactionsList =  MutableLiveData<List<FullTransaction>>()
    val transactionsList: LiveData<List<FullTransaction>> = _transactionsList


    init {
        transactionRepository.getAllTransactions()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{_transactionsList.value = it}
            .addTo(dataCompositeDisposable)
    }

}