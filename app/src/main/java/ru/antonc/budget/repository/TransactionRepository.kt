package ru.antonc.budget.repository

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.antonc.budget.data.AppDatabase
import ru.antonc.budget.data.entities.Category
import ru.antonc.budget.data.entities.Transaction
import ru.antonc.budget.data.entities.TransactionType
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val database: AppDatabase,
    private val dataDisposable: CompositeDisposable
) {

    fun getAllTransactions() = database.transactionDAO().getAll()
        .map { transitions -> transitions.filter { transaction -> transaction.id.isNotEmpty() } }
        .subscribeOn(Schedulers.io())

    fun getAllCategories() = database.categoryDAO().getAll()

    fun getAllAccounts() = database.accountDAO().getAll()

    fun getOrCreateTransaction(
        transactionId: String,
        transactionType: TransactionType = TransactionType.NOT_SET
    ): Flowable<Transaction> = database.transactionDAO().getTransactionById(transactionId)
        .doOnNext {
            if (it.isEmpty())
                createTransaction(transactionId, transactionType)
        }
        .filter { it.isNotEmpty() }
        .map { it.first() }
        .subscribeOn(Schedulers.io())

    private fun createTransaction(id: String, transactionType: TransactionType) {
        Transaction(
            id = id,
            type = transactionType,
            date = Calendar.getInstance().timeInMillis
        ).also { transaction ->
            database.transactionDAO().insert(transaction)
                .subscribeOn(Schedulers.io())
                .subscribe()
                .addTo(dataDisposable)
        }
    }

    fun saveTransaction(transaction: Transaction) {
        database.transactionDAO().insert(transaction)
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(dataDisposable)
    }

    fun createCategory(categoryName: String) {
        database.categoryDAO().insert(Category(name = categoryName))
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(dataDisposable)
    }

    fun selectCategory(
        category: Category,
        transactionId: String
    ) {
        database.transactionDAO().getTransactionById(transactionId)
            .filter { it.isNotEmpty() }
            .map { it.first() }
            .doOnNext { transaction ->
                transaction.category = category
            }
            .firstElement()
            .flatMapCompletable { transaction ->
                database.transactionDAO().update(transaction)
            }
            .subscribe()
            .addTo(dataDisposable)
    }

    fun deleteTransaction(id: String = "") {
        database.transactionDAO().deleteTransaction(id)
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(dataDisposable)
    }


}