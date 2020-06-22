package ru.antonc.budget.repository

import android.util.Log
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

    fun getAllCategories() = database.categoryDAO().getAll()

    fun getAllAccounts() = database.accountDAO().getAll()


    fun getAccount(id: Long) = database.accountDAO().getAccountById(id)
        .subscribeOn(Schedulers.io())

    fun getCategory(id: Long) = database.categoryDAO().getCategoryById(id)
        .subscribeOn(Schedulers.io())

    fun getOrCreateTransaction(
        transactionId: String,
        transactionType: TransactionType = TransactionType.NOT_SET
    ): Flowable<FullTransaction> = database.transactionDAO().getTransactionById(transactionId)
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
                transaction.info.categoryId = category.id
            }
            .firstElement()
            .flatMapCompletable { transaction ->
                database.transactionDAO().update(transaction.info)
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

    fun saveAccount(account: Account) {
        database.accountDAO().insert(account)
            .subscribeOn(Schedulers.io())
            .subscribe{
                Log.d("dako", "success saved")
            }
            .addTo(dataDisposable)
    }


}