package ru.antonc.budget.ui.overview

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.navigation.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import ru.antonc.budget.data.entities.Account
import ru.antonc.budget.data.entities.FullTransaction
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject
import kotlin.math.min

class OverviewViewModel @Inject constructor(
    repository: TransactionRepository
) : BaseViewModel() {

    val accountsList: LiveData<List<Account>> = repository.getAllAccounts()
        .map { accountsList -> accountsList.subList(0, min(accountsList.size, 4)) }

    private val _transactionsList = MutableLiveData<List<FullTransaction>>()
    val transactionsList: LiveData<List<FullTransaction>> = _transactionsList


    init {
        repository.getAllTransactions()
            .map { transactionsList -> transactionsList.subList(0, min(transactionsList.size, 5)) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _transactionsList.postValue(it)
            }
            .addTo(dataCompositeDisposable)
    }

    fun addIncome(view: View) {
        view.findNavController().navigate(
            OverviewFragmentDirections.actionOverviewFragmentToTransactionFragment(
                "",
                TransactionType.INCOMES.name
            )
        )
    }

    fun addExpense(view: View) {
        view.findNavController().navigate(
            OverviewFragmentDirections.actionOverviewFragmentToTransactionFragment(
                "",
                TransactionType.EXPENSES.name
            )
        )
    }

    fun toAccountsList(view: View) {
        view.findNavController().navigate(
            OverviewFragmentDirections.actionOverviewFragmentToAccountsFragment()
        )
    }

}