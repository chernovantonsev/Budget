package ru.antonc.budget.ui.accounts

import androidx.lifecycle.LiveData
import ru.antonc.budget.data.entities.Account
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class AccountsViewModel @Inject constructor(
    private val repository: TransactionRepository
) : BaseViewModel() {

//    private var categoryName: String = ""
//
    val accountsList: LiveData<List<Account>> = repository.getAllAccounts()
//
//    fun setCategoryName(name: String) {
//        categoryName = name
//    }
//
//    fun saveCategory() {
//        transactionRepository.createCategory(categoryName)
//    }
//
//    fun selectCategory(category: Category, transactionId: String) {
//        transactionRepository.selectCategory(category, transactionId)
//    }
}