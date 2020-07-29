package ru.antonc.budget.repository

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
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
    private val dataDisposable: CompositeDisposable,
    private val ioDispatcher: CoroutineDispatcher
) {

    fun getAllTransactions() = database.transactionDAO().getAll()
        .distinctUntilChanged()
        .subscribeOn(Schedulers.io())

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
            database.transactionDAO().insertSuspend(transaction)
        }

        if (isNeedActualizeBalance) actualizeAccountBalance(transaction)
    }

    fun createCategory(categoryName: String, transactionType: TransactionType) {
        database.categoryDAO().insert(Category(name = categoryName, type = transactionType))
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(dataDisposable)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        withContext(ioDispatcher) {
            database.transactionDAO().delete(transaction)
        }

        actualizeAccountBalance(transaction)
    }

    suspend fun deleteEmptyTransaction() {
        withContext(ioDispatcher) {
            database.transactionDAO().deleteTransaction("")
        }
    }

    fun saveAccount(account: Account) {
        database.accountDAO().insert(account)
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(dataDisposable)
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
                }?.let(::saveAccount)
            }
        }
    }
}