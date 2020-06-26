package ru.antonc.budget.ui.statistics.piechart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.addTo
import ru.antonc.budget.data.entities.FullTransaction
import ru.antonc.budget.data.entities.LegendItem
import ru.antonc.budget.data.entities.StatisticsPage
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.repository.StatisticsRepository
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import ru.antonc.budget.util.FORMAT_DECIMAL
import ru.antonc.budget.util.extenstions.getDayToCompare
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class PieChartStatisticsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val statisticsRepository: StatisticsRepository
) : BaseViewModel() {

    private val _data = MutableLiveData<PieChartData>()
    val data: LiveData<PieChartData> = _data

    private val type = BehaviorRelay.create<StatisticsPage>()

    init {
        Flowables.combineLatest(
            type.toFlowable(BackpressureStrategy.LATEST)
                .map {
                    when (it) {
                        StatisticsPage.INCOME -> TransactionType.INCOME
                        else -> TransactionType.EXPENSE
                    }
                },
            getTransactionInDateRange()
        )
            .map { (transactionType, transactions) ->
                convertToCategoryStatistics(transactions
                    .filter { transaction ->
                        transaction.info.type == transactionType
                                && transaction.category != null
                    })
            }
            .map {
                PieChartData(
                    itemsLegend = it,
                    totalSum = "${FORMAT_DECIMAL.format(it.sumByDouble { item -> item.sum })} ₽"
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _data.postValue(it)
            }
            .addTo(dataCompositeDisposable)
    }

    private fun getTransactionInDateRange() = Flowables.combineLatest(
        transactionRepository.getAllTransactions(),
        statisticsRepository.dataRangeValue
    )
    { transactions, (start, end) ->
        with(Calendar.getInstance()) {
            val startDay = getDayToCompare(start)
            val endDay = getDayToCompare(end)

            return@combineLatest transactions.filter { transaction ->
                getDayToCompare(transaction.info.date).let { transactionDay ->
                    return@filter transactionDay in startDay..endDay
                }
            }
        }
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
        type.accept(StatisticsPage.valueOf(typeName))
    }

    data class PieChartData(
        val totalSum: String,
        val itemsLegend: List<LegendItem>
    )
}