package ru.antonc.budget.repository

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.antonc.budget.data.AppDatabase
import ru.antonc.budget.data.entities.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val database: AppDatabase,
    private val dataDisposable: CompositeDisposable
) {

    fun getAllTransactions() = database.transactionDAO().getAll()
        .map { transitions -> transitions.filter { fullTransaction -> fullTransaction.info.id.isNotEmpty() } }
        .subscribeOn(Schedulers.io())

    fun getCategoriesByType(transactionType: TransactionType) =
        database.categoryDAO().getAll()
            .map { categories ->
                categories.filter { category ->
                    category.type == transactionType
                }
            }
            .subscribeOn(Schedulers.io())

    fun getAllAccounts() = database.accountDAO().getAll()

    fun getAccount(id: Long) = database.accountDAO().getAccountById(id)
        .subscribeOn(Schedulers.io())

    fun getTransactionById(id: String) = database.transactionDAO().getTransactionById(id)
        .subscribeOn(Schedulers.io())

    fun getOrCreateTransaction(
        transactionId: String,
        transactionType: TransactionType?
    ): Flowable<FullTransaction> = database.transactionDAO().getFullTransaction(transactionId)
        .doOnNext {
            if (it.isEmpty() && transactionType != null)
                createNewTransaction(transactionType)
        }
        .filter { it.isNotEmpty() }
        .map { it.first() }
        .subscribeOn(Schedulers.io())

    private fun createNewTransaction(transactionType: TransactionType) {
        Transaction(
            type = transactionType,
            date = Calendar.getInstance().timeInMillis
        ).let { transaction -> saveTransaction(transaction) }
    }

    fun saveTransaction(transaction: Transaction, isNeedActualizeBalance: Boolean = false) {
        database.transactionDAO().insert(transaction)
            .doOnComplete { if (isNeedActualizeBalance) actualizeAccountBalance(transaction) }
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(dataDisposable)
    }

    fun createCategory(categoryName: String, transactionType: TransactionType) {
        database.categoryDAO().insert(Category(name = categoryName, type = transactionType))
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(dataDisposable)
    }

    fun selectCategory(
        category: Category,
        transactionId: String
    ) {
        database.transactionDAO().getFullTransaction(transactionId)
            .filter { it.isNotEmpty() }
            .map { it.first() }
            .doOnNext { transaction ->
                transaction.info.categoryId = category.id
            }
            .firstElement()
            .flatMapCompletable { transaction ->
                database.transactionDAO().update(transaction.info)
            }
            .subscribe()
            .addTo(dataDisposable)
    }

    fun deleteTransaction(transaction: Transaction) {
        database.transactionDAO().delete(transaction)
            .doOnComplete { actualizeAccountBalance(transaction) }
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(dataDisposable)
    }

    fun deleteEmptyTransaction() {
        database.transactionDAO().deleteTransaction("")
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(dataDisposable)
    }

    fun saveAccount(account: Account) {
        database.accountDAO().insert(account)
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(dataDisposable)
    }

    private fun actualizeAccountBalance(transaction: Transaction) {
        database.transactionDAO().getTransactionByAccountId(transaction.accountId)
            .subscribeOn(Schedulers.io())
            .blockingFirst()?.let { transactions ->
                var actualizeBalance = 0.0
                transactions.forEach { transaction ->
                    if (transaction.type == TransactionType.INCOMES) {
                        actualizeBalance += transaction.sum
                    } else if (transaction.type == TransactionType.EXPENSES) {
                        actualizeBalance -= transaction.sum
                    }
                }

                database.accountDAO().getAccountById(transaction.accountId)
                    .blockingFirst()?.let { account ->
                        account.balance = account.initialBalance + actualizeBalance
                        saveAccount(account)
                    }
            }
    }
}