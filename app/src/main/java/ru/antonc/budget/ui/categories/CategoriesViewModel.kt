package ru.antonc.budget.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import ru.antonc.budget.data.entities.Category
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private var categoryName: String = ""

    private val transactionId = BehaviorRelay.create<String>()

    val categoriesList: LiveData<List<Category>> = transactionRepository.getAllCategories()

    private val _selectedCategoryId = MutableLiveData<Long>()
    val selectedCategoryId: LiveData<Long> = _selectedCategoryId

    init {
        transactionId.toFlowable(BackpressureStrategy.LATEST)
            .flatMap { id ->
                transactionRepository.getTransactionById(id)
            }
            .map { transaction -> transaction.categoryId }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _selectedCategoryId.value = it }
            .addTo(dataCompositeDisposable)
    }


    fun setTransactionId(id: String = "") {
        transactionId.accept(id)
    }

    fun setCategoryName(name: String) {
        categoryName = name
    }

    fun saveCategory() {
        transactionRepository.createCategory(categoryName)
    }

    fun selectCategory(category: Category, transactionId: String) {
        transactionRepository.selectCategory(category, transactionId)
    }
}