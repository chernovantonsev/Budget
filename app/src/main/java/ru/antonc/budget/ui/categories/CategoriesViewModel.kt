package ru.antonc.budget.ui.categories

import androidx.lifecycle.LiveData
import ru.antonc.budget.data.entities.Category
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private var categoryName: String = ""

    val categoriesList: LiveData<List<Category>> = transactionRepository.getAllCategories()

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