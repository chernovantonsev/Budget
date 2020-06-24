package ru.antonc.budget.ui.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import ru.antonc.budget.data.entities.Account
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class AccountsViewModel @Inject constructor(
    repository: TransactionRepository
) : BaseViewModel() {

    val accountsList: LiveData<List<Account>> = repository.getAllAccounts()
        .map { accountsList -> accountsList.subList(0, 4)  }
}