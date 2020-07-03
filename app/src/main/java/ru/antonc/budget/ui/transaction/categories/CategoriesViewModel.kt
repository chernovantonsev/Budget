package ru.antonc.budget.ui.transaction.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import ru.antonc.budget.data.entities.Category
import ru.antonc.budget.data.entities.Transaction
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.data.entities.common.EventContent
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private var categoryName: String = ""

    private val transactionId = BehaviorRelay.create<String>()
    private val transactionType = BehaviorRelay.create<TransactionType>()


    private val _categoriesList = MutableLiveData<List<Category>>()
    val categoriesList: LiveData<List<Category>> = _categoriesList

    private val _selectedCategoryId = MutableLiveData<Long>()
    val selectedCategoryId: LiveData<Long> = _selectedCategoryId

    private val _transaction = MutableLiveData<Transaction>()
    val transaction: LiveData<Transaction> = _transaction

    private val _navigateEvent = MutableLiveData<EventContent<Boolean>>()
    val navigateEvent: LiveData<EventContent<Boolean>> =
        _navigateEvent

    init {
        transactionType.toFlowable(BackpressureStrategy.LATEST)
            .flatMap { transactionType ->
                transactionRepository.getCategoriesByType(transactionType)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _categoriesList.value = it }
            .addTo(dataCompositeDisposable)


        transactionId.toFlowable(BackpressureStrategy.LATEST)
            .flatMap { id ->
                transactionRepository.getTransactionById(id)
            }
            .doOnNext {
                _transaction.postValue(it)
            }
            .map { transaction -> transaction.categoryId }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _selectedCategoryId.value = it }
            .addTo(dataCompositeDisposable)
    }


    fun setTransactionInfo(id: String = "", transactionTypeName: String) {
        TransactionType.fromValue(transactionTypeName)?.let { transactionType ->
            this.transactionType.accept(transactionType)
        }
        transactionId.accept(id)
    }

    fun setCategoryName(name: String = "") {
        categoryName = name
    }

    fun saveCategory() {
        transactionType.value?.let { transactionType ->
            transactionRepository.createCategory(categoryName, transactionType)
        }
    }

    fun selectCategory(category: Category, transactionId: String) {
        transaction.value?.let { transaction ->
            if (transaction.id.isEmpty())
                transaction.id = UUID.randomUUID().toString()
            transaction.categoryId = category.id
            transactionRepository.saveTransaction(transaction, transactionId.isEmpty())
        }

        _navigateEvent.postValue(EventContent(transactionId.isEmpty()))
    }
}