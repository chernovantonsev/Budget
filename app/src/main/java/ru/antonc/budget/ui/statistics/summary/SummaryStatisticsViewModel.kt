package ru.antonc.budget.ui.statistics.summary

import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.Dispatchers
import ru.antonc.budget.data.entities.FullTransaction
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.repository.StatisticsRepository
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import ru.antonc.budget.util.extenstions.combineWith
import ru.antonc.budget.util.extenstions.getDayToCompare
import java.util.*
import javax.inject.Inject

class SummaryStatisticsViewModel @Inject constructor(
    transactionRepository: TransactionRepository,
    statisticsRepository: StatisticsRepository
) : BaseViewModel() {

    val data = transactionRepository.getAllTransactionsS()
        .combineWith(statisticsRepository.dateRangeValueL) { transactions, (start, end) ->
            return@combineWith filterByDate(transactions, start, end)
        }
        .switchMap {
            liveData(Dispatchers.Default) { emit(getSummary(it)) }
        }

    private fun filterByDate(
        transactions: List<FullTransaction>,
        start: Long,
        end: Long
    ): List<FullTransaction> {
        with(Calendar.getInstance()) {
            val startDay = getDayToCompare(start)
            val endDay = getDayToCompare(end)

            return transactions.filter { transaction ->
                getDayToCompare(transaction.info.date).let { transactionDay ->
                    return@filter transactionDay in startDay..endDay
                }
            }
        }
    }

    private fun getSummary(transactions: List<FullTransaction>): SummaryStatisticsData {
        var incomes = 0.0
        var expenses = 0.0

        transactions.forEach { transaction ->
            if (transaction.info.type == TransactionType.INCOMES) {
                incomes += transaction.info.sum
            } else if (transaction.info.type == TransactionType.EXPENSES) {
                expenses -= transaction.info.sum
            }
        }

        return SummaryStatisticsData(
            incomes = incomes,
            expenses = expenses,
            balance = incomes + expenses
        )
    }

    data class SummaryStatisticsData(
        val incomes: Double,
        val expenses: Double,
        val balance: Double
    )
}