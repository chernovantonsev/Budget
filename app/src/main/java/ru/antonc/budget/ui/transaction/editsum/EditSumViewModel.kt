package ru.antonc.budget.ui.transaction.editsum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import ru.antonc.budget.data.entities.Transaction
import ru.antonc.budget.data.entities.common.EventContent
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import ru.antonc.budget.util.extenstions.default
import javax.inject.Inject

class EditSumViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val _navigateToCategoriesEvent = MutableLiveData<EventContent<Pair<String, String>>>()
    val navigateToCategoriesEvent: LiveData<EventContent<Pair<String, String>>> =
        _navigateToCategoriesEvent

    private val _numberString = MutableLiveData<String>().default("0")
    val numberString: LiveData<String> = _numberString

    private val transaction = BehaviorRelay.create<Transaction>()

    init {
        transactionRepository.getTransactionById("")
            .subscribe { transaction.accept(it) }
            .addTo(dataCompositeDisposable)
    }

    fun setNumPadResult(numberString: String) {
        _numberString.value = numberString
    }

    fun goToCategories() {
        transaction.value?.let { transaction ->
            numberString.value?.toDoubleOrNull()?.let {
                transaction.sum = it
                transactionRepository.saveTransaction(transaction)

                _navigateToCategoriesEvent.value =
                    EventContent(transaction.id to transaction.type.name)
            }
        }
    }

}