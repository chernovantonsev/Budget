package ru.antonc.budget.ui.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import ru.antonc.budget.util.extenstions.getDayToCompare
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class TransactionsListViewModel @Inject constructor(
    transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val _transactionsList = MutableLiveData<List<TransactionListItem>>()
    val transactionsList: LiveData<List<TransactionListItem>> = _transactionsList

    init {
        transactionRepository.getAllTransactions()
            .map { transactions ->
                transactions.sortedByDescending { transaction -> transaction.info.date }
            }
            .map { transactions ->
                transactions.groupBy { transaction ->
                    Calendar.getInstance().getDayToCompare(transaction.info.date)
                }
            }
            .map { groupedTransactions ->
                ArrayList<TransactionListItem>().apply {
                    groupedTransactions.keys.forEach { key ->
                        val dateItem = TransactionDateListItem()
                        add(dateItem)

                        groupedTransactions[key]?.forEach { transaction ->
                            add(TransactionGeneralListItem(transaction))

                            if (transaction.info.type == TransactionType.INCOMES)
                                dateItem.sum += transaction.info.sum
                            else dateItem.sum -= transaction.info.sum
                        }

                        groupedTransactions[key]?.first()?.let { transaction ->
                            dateItem.date =
                                SimpleDateFormat("dd MMMM, EEEE", Locale.getDefault()).format(
                                    transaction.info.date
                                )
                        }
                    }
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _transactionsList.value = it }
            .addTo(dataCompositeDisposable)
    }

}