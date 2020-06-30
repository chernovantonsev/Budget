package ru.antonc.budget.ui.menu.categories.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import ru.antonc.budget.data.entities.Category
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class CategoriesListViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val transactionType = BehaviorRelay.create<TransactionType>()

    private val _categoriesList = MutableLiveData<List<Category>>()
    val categoriesList: LiveData<List<Category>> = _categoriesList

    init {
        transactionType.toFlowable(BackpressureStrategy.LATEST)
            .flatMap { transactionType ->
                transactionRepository.getCategoriesByType(transactionType)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _categoriesList.value = it }
            .addTo(dataCompositeDisposable)
    }

    fun setTransactionType(transactionType: TransactionType) {
        this.transactionType.accept(transactionType)
    }
}