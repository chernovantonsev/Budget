package ru.antonc.budget.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.antonc.budget.data.AppDatabase
import ru.antonc.budget.data.entities.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val database: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher
) {

    fun getAllTransactions() = database.transactionDAO().getAll()

    fun getCategoriesByType(transactionType: TransactionType) =
        database.categoryDAO().getCategoriesByTransactionsType(transactionType)

    fun getAllAccounts() = database.accountDAO().getAll()

    suspend fun getAccount(id: Long) = database.accountDAO().getAccountById(id)

    suspend fun getTransactionById(id: String) = database.transactionDAO().getTransactionById(id)

    suspend fun getOrCreateTransaction(
        transactionId: String = "",
        transactionType: TransactionType?
    ): FullTransaction {
        val fullTransaction =
            withContext(ioDispatcher) {
                database.transactionDAO().getFullTransactionById(transactionId)
            } ?: FullTransaction()
        if (fullTransaction.isNew() && transactionType != null) {
            fullTransaction.info = Transaction(
                type = transactionType,
                date = Calendar.getInstance().timeInMillis
            )
            saveTransaction(fullTransaction.info, false)
        }
        return fullTransaction
    }

    suspend fun saveTransaction(transaction: Transaction, isNeedActualizeBalance: Boolean = false) {
        withContext(ioDispatcher) {
            database.transactionDAO().insert(transaction)
        }

        if (isNeedActualizeBalance) actualizeAccountBalance(transaction)
    }

    suspend fun createCategory(categoryName: String, transactionType: TransactionType) {
        withContext(ioDispatcher) {
            database.categoryDAO().insert(Category(name = categoryName, type = transactionType))
        }
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        withContext(ioDispatcher) {
            database.transactionDAO().delete(transaction)
        }

        actualizeAccountBalance(transaction)
    }

    suspend fun deleteEmptyTransaction() {
        withContext(ioDispatcher) {
            database.transactionDAO().deleteTransactionById("")
        }
    }

    suspend fun saveAccount(account: Account) {
        withContext(ioDispatcher) {
            database.accountDAO().insert(account)
        }
    }

    private suspend fun actualizeAccountBalance(transaction: Transaction) {
        withContext(ioDispatcher) {
            with(database.transactionDAO().getTransactionByAccountId(transaction.accountId)) {
                var actualizeBalance = 0.0

                forEach { transaction ->
                    if (transaction.id.isNotEmpty()) {
                        if (transaction.type == TransactionType.INCOMES) {
                            actualizeBalance += transaction.sum
                        } else if (transaction.type == TransactionType.EXPENSES) {
                            actualizeBalance -= transaction.sum
                        }
                    }
                }

                database.accountDAO().getAccountById(transaction.accountId)?.apply {
                    balance = initialBalance + actualizeBalance
                }?.let { ::saveAccount }
            }
        }
    }
}