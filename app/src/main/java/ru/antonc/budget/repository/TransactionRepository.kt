package ru.antonc.budget.repository

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
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
    private val dataDisposable: CompositeDisposable
) {

    fun getAllTransactions() = transactionDAO.getAll()

    fun getAllCategories() = categoryDAO.getAll()

    fun getTransaction(
        transactionId: String,
        transactionType: TransactionType = TransactionType.NOT_SET
    ): Flowable<Transaction> = transactionDAO.getTransactionById(transactionId)
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
            transactionDAO.insert(transaction)
                .subscribeOn(Schedulers.io())
                .subscribe()
                .addTo(dataDisposable)
        }
    }

    fun saveTransaction(transaction: Transaction) {
        transactionDAO.insert(transaction)
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(dataDisposable)
    }

    fun createCategory(categoryName: String) {
        categoryDAO.insert(Category(name = categoryName))
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(dataDisposable)
    }

    fun selectCategory(
        category: Category,
        transactionId: String
    ) {
        getTransaction(transactionId)
            .doOnNext { transaction ->
                transaction.category = category
            }
            .firstElement()
            .flatMapCompletable { transaction ->
                transactionDAO.update(transaction)
            }
            .subscribe()
            .addTo(dataDisposable)
    }
}