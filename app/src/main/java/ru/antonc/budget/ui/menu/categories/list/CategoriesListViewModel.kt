package ru.antonc.budget.ui.menu.categories.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import ru.antonc.budget.data.entities.Category
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class CategoriesListViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val transactionType = MutableLiveData<TransactionType>()

    val categoriesList: LiveData<List<Category>> = transactionType.switchMap { transactionType ->
        transactionRepository.getCategoriesByType(transactionType)
    }

    fun setTransactionType(transactionType: TransactionType) {
        this.transactionType.value = transactionType
    }
}