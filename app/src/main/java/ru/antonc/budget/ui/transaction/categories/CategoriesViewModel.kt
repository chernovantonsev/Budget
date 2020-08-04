package ru.antonc.budget.ui.transaction.categories

import androidx.lifecycle.*
import kotlinx.coroutines.launch
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

    private val transactionId = MutableLiveData<String>()
    private val transactionType = MutableLiveData<TransactionType>()

    val categoriesList: LiveData<List<Category>> = transactionType.switchMap { transactionType ->
        transactionRepository.getCategoriesByType(transactionType)
    }

    val transaction: LiveData<Transaction> = transactionId.switchMap { id ->
        liveData { emit(transactionRepository.getTransactionById(id)) }
    }

    val selectedCategoryId: LiveData<Long> = transaction.map { transaction ->
        transaction.categoryId
    }

    private val _navigateEvent = MutableLiveData<EventContent<Boolean>>()
    val navigateEvent: LiveData<EventContent<Boolean>> =
        _navigateEvent


    fun setTransactionInfo(id: String = "", transactionTypeName: String) {
        TransactionType.fromValue(transactionTypeName)?.let { transactionType ->
            this.transactionType.value = transactionType
        }
        transactionId.value = id
    }

    fun setCategoryName(name: String = "") {
        categoryName = name
    }

    fun saveCategory() {
        transactionType.value?.let { transactionType ->
            viewModelScope.launch {
                transactionRepository.createCategory(categoryName, transactionType)
            }
        }
    }

    fun selectCategory(category: Category, transactionId: String) = viewModelScope.launch {
        transaction.value?.let { transaction ->
            if (transaction.id.isEmpty())
                transaction.id = UUID.randomUUID().toString()
            transaction.categoryId = category.id
            transactionRepository.saveTransaction(transaction, transactionId.isEmpty())
        }

        _navigateEvent.postValue(EventContent(transactionId.isEmpty()))
    }
}