package ru.antonc.budget.ui.accounts.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import ru.antonc.budget.data.entities.Account
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import ru.antonc.budget.util.extenstions.default
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val accountId = BehaviorRelay.create<Long>()

    private val _account = MutableLiveData<Account>().default(Account())
    val account: LiveData<Account> = _account

    init {
        accountId.toFlowable(BackpressureStrategy.LATEST)
            .flatMap { id -> transactionRepository.getAccount(id) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _account.value = it }
            .addTo(dataCompositeDisposable)
    }

    fun setBalance(sumString: String) {
        (sumString.toDoubleOrNull() ?: 0.0).let {
            account.value?.let { account ->
                account.balance = it
            }
        }
    }

    fun saveAccount() {
        account.value?.let { account ->
            transactionRepository.saveAccount(account)
        }
    }

    fun setAccountId(id: Long) {
        if (id != -1L)
            accountId.accept(id)
    }

}