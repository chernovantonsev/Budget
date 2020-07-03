package ru.antonc.budget.ui.statistics.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.addTo
import ru.antonc.budget.data.entities.FullTransaction
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.repository.StatisticsRepository
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import ru.antonc.budget.util.extenstions.getDayToCompare
import java.util.*
import javax.inject.Inject

class SummaryStatisticsViewModel @Inject constructor(
    transactionRepository: TransactionRepository,
    statisticsRepository: StatisticsRepository
) : BaseViewModel() {

    private val _data = MutableLiveData<SummaryStatisticsData>()
    val data: LiveData<SummaryStatisticsData> = _data

    init {
        Flowables.combineLatest(
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
            .map(::getSummary)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _data.postValue(it)
            }
            .addTo(dataCompositeDisposable)
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