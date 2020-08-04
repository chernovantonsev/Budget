package ru.antonc.budget.ui.statistics.piechart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.antonc.budget.data.entities.FullTransaction
import ru.antonc.budget.data.entities.LegendItem
import ru.antonc.budget.data.entities.StatisticsPage
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.repository.StatisticsRepository
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import ru.antonc.budget.util.FORMAT_DECIMAL
import ru.antonc.budget.util.extenstions.combineWith
import ru.antonc.budget.util.extenstions.getDayToCompare
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class PieChartStatisticsViewModel @Inject constructor(
    transactionRepository: TransactionRepository,
    statisticsRepository: StatisticsRepository
) : BaseViewModel() {

    private val transactionsInDateRange = transactionRepository.getAllTransactions()
        .combineWith(statisticsRepository.dateRangeValue) { transactions, (start, end) ->
            with(Calendar.getInstance()) {
                val startDay = getDayToCompare(start)
                val endDay = getDayToCompare(end)

                return@combineWith transactions.filter { transaction ->
                    getDayToCompare(transaction.info.date).let { transactionDay ->
                        return@filter transactionDay in startDay..endDay
                    }
                }
            }
        }

    private val _type = MutableLiveData<StatisticsPage>()
    private val type = _type.map {
        return@map when (it) {
            StatisticsPage.INCOME -> TransactionType.INCOMES
            else -> TransactionType.EXPENSES
        }
    }

    val data: LiveData<PieChartData> =
        transactionsInDateRange.combineWith(type) { transactions, transactionType ->
            return@combineWith convertToCategoryStatistics(transactions
                .filter { transaction ->
                    transaction.info.type == transactionType
                            && transaction.category != null
                })
        }.map {
            PieChartData(
                itemsLegend = it,
                totalSum = "${FORMAT_DECIMAL.format(it.sumByDouble { item -> item.sum })} ₽"
            )
        }

    private fun convertToCategoryStatistics(transactions: List<FullTransaction>): List<LegendItem> {
        val result = ArrayList<LegendItem>()

        val totalSum = transactions.sumByDouble { transaction -> transaction.info.sum }

        transactions
            .groupBy { transaction ->
                transaction.category
            }.also { mapGroupedTransactions ->
                mapGroupedTransactions.keys.forEach {

                    it?.let { category ->
                        val sum =
                            mapGroupedTransactions[category]?.sumByDouble { transaction -> transaction.info.sum }
                                ?: 0.0

                        result.add(
                            LegendItem(
                                category.id,
                                category.name,
                                sum,
                                (sum / totalSum) * 100
                            )
                        )
                    }
                }
            }
        return result
    }


    fun setDataTypeName(typeName: String) {
        _type.value = StatisticsPage.valueOf(typeName)
    }

    data class PieChartData(
        val totalSum: String,
        val itemsLegend: List<LegendItem>
    )
}