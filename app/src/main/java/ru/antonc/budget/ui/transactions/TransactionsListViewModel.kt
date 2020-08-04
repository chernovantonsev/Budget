package ru.antonc.budget.ui.transactions

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import ru.antonc.budget.data.entities.FullTransaction
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

    val transactionsList: LiveData<List<TransactionListItem>> =
        transactionRepository.getAllTransactionsS().switchMap { transactions ->
            liveData {
                emit(groupTransactionByDate(transactions))
            }
        }

    private suspend fun groupTransactionByDate(transactions: List<FullTransaction>): List<TransactionListItem> {
        return viewModelScope.async(Dispatchers.Default) {
            val groupedTransactions = transactions.groupBy { transaction ->
                Calendar.getInstance().getDayToCompare(transaction.info.date)
            }
            return@async ArrayList<TransactionListItem>().apply {
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
        }.await()
    }
}