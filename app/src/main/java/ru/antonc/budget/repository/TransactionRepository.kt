package ru.antonc.budget.repository

import kotlinx.coroutines.CoroutineDispatcher
import ru.antonc.budget.data.dao.CategoryDAO
import ru.antonc.budget.data.dao.TransactionDAO
import ru.antonc.budget.data.entities.Transaction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val transactionDAO: TransactionDAO,
    private val categoryDAO: CategoryDAO,

    private val ioDispatcher: CoroutineDispatcher
) {


    fun getTransactionById(transactionId: Int) = transactionDAO.getTransactionById(transactionId)

    suspend fun createTransaction(transaction: Transaction) {
        transactionDAO.insert(transaction)
    }

}