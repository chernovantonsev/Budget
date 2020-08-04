package ru.antonc.budget.ui.menu.categories

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class CustomizationCategoriesViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private var categoryName: String = ""

    fun setCategoryName(name: String = "") {
        categoryName = name
    }

    fun saveCategory(transactionType: TransactionType) {
        viewModelScope.launch {
            transactionRepository.createCategory(categoryName, transactionType)
        }
    }

}