package ru.antonc.budget.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.antonc.budget.data.dao.CategoryDAO
import ru.antonc.budget.data.dao.TransactionDAO
import ru.antonc.budget.data.entities.Category
import ru.antonc.budget.data.entities.Transaction
import ru.antonc.budget.data.entities.TransactionType
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val transactionDAO: TransactionDAO,
    private val categoryDAO: CategoryDAO,
    private val ioDispatcher: CoroutineDispatcher
) {

    fun getAllTransactions() = transactionDAO.getAll()

    fun getAllCategories() = categoryDAO.getAll()

    fun getTransaction(
        transactionId: String,
        transactionType: TransactionType
    ): LiveData<Transaction> = transactionDAO.getTransactionById(transactionId)
            .switchMap { transaction ->
                if (transaction == null)
                    createTransaction(transactionType)
                else liveData { emit(transaction!!) }
            }

    private fun createTransaction(
        transactionType: TransactionType
    ): LiveData<Transaction> {
        return liveData {
            Transaction(
                type = transactionType,
                date = Calendar.getInstance().timeInMillis
            )
                .also { newTransaction ->
                    saveTransaction(newTransaction)
                    emit(newTransaction)
                }
        }
    }


    suspend fun saveTransaction(transaction: Transaction) {
        withContext(ioDispatcher) {
            transactionDAO.insert(transaction)
        }
    }

    suspend fun createCategory(categoryName: String) {
        withContext(ioDispatcher) {
            categoryDAO.insert(Category(name = categoryName))
        }
    }

    suspend fun selectCategory(
        category: Category,
        transactionId: String
    ) {
        withContext(ioDispatcher) {
            transactionDAO.getTransactionById(transactionId).value?.let { transaction ->
                transaction.category = category
                transactionDAO.update(transaction)
            }
        }
    }
}