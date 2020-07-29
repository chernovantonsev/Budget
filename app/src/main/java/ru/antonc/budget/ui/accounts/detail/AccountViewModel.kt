package ru.antonc.budget.ui.accounts.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val accountId = MutableLiveData<Long>()

    val account = accountId.switchMap { id ->
        liveData {
            emit(transactionRepository.getAccount(id))
        }
    }

    fun setBalance(sumString: String) {
        (sumString.toDoubleOrNull() ?: 0.0).let {
            account.value?.let { account ->
                account.initialBalance = it
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
            accountId.value = id
    }

}