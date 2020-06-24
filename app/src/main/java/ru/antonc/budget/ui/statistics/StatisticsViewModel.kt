package ru.antonc.budget.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.addTo
import ru.antonc.budget.data.entities.CategoryStatistics
import ru.antonc.budget.data.entities.FullTransaction
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.data.entities.common.EventContent
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import ru.antonc.budget.util.extenstions.getDayToCompare
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class StatisticsViewModel @Inject constructor(
    transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val _pages = MutableLiveData<ArrayList<Pair<String, List<CategoryStatistics>>>>()
    val pages: LiveData<ArrayList<Pair<String, List<CategoryStatistics>>>> = _pages

    private val _dateRangeValue =
        BehaviorRelay.createDefault(
            Pair(
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis
            )
        )
    val dateRangeValue: LiveData<String> =
        LiveDataReactiveStreams.fromPublisher(_dateRangeValue.toFlowable(BackpressureStrategy.LATEST))
            .map { (start, end) ->
                if (end == 0L || start == end) {
                    with(Calendar.getInstance()) {
                        if (getDayToCompare() == getDayToCompare(start))
                            return@map "Сегодня"
                    }

                    return@map formatDate(start)
                } else "${formatDate(start)} - ${formatDate(end)}"
            }

    private val _datePickerEvent = MutableLiveData<EventContent<Long>>()
    val datePickerEvent: LiveData<EventContent<Long>> = _datePickerEvent

    init {
        Flowables.combineLatest(
            transactionRepository.getAllTransactions(),
            _dateRangeValue.toFlowable(BackpressureStrategy.LATEST)
        ) { transactions, (start, end) ->
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
            .map { transactions ->
                ArrayList<Pair<String, List<CategoryStatistics>>>().apply {
                    add(
                        "Доходы" to convertToCategoryStatistics(transactions
                            .filter { transaction ->
                                transaction.info.type == TransactionType.INCOME
                                        && transaction.category != null
                            })
                    )

                    add(
                        "Расходы" to convertToCategoryStatistics(transactions
                            .filter { transaction ->
                                transaction.info.type == TransactionType.EXPENSE
                                        && transaction.category != null
                            })
                    )
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _pages.postValue(it)
            }
            .addTo(dataCompositeDisposable)
    }

    private fun convertToCategoryStatistics(transactions: List<FullTransaction>): List<CategoryStatistics> {
        val result = ArrayList<CategoryStatistics>()

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
                            CategoryStatistics(
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

    fun selectDateRange() {
        _dateRangeValue.value?.let { (start, _) ->
            _datePickerEvent.value = EventContent(start)
        }
    }

    fun setDate(date: Long) {
        setDate(date, date)
    }

    fun setDate(dateStart: Long, dateEnd: Long) {
        _dateRangeValue.accept(dateStart to dateEnd)
    }

    private fun formatDate(date: Long) =
        SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)
}