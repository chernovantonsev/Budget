package ru.antonc.budget.ui.transaction

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ru.antonc.budget.data.entities.Category
import ru.antonc.budget.data.entities.Transaction
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class TransactionViewModel @Inject constructor() : BaseViewModel() {

    private val _transaction = MutableLiveData<Transaction>()
    val transaction: LiveData<Transaction>
        get() = _transaction

    val selectedCategoryName: LiveData<String> = Transformations.map(transaction) {transaction ->
        "Test"
    }


    fun setTransactionDetails(id: String, type: String = "") {

    }

    fun goToCategories(view: View) {

    }

    fun saveTransaction() {

    }

}