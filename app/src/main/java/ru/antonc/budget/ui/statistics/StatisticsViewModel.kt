package ru.antonc.budget.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import ru.antonc.budget.data.entities.CategoryStatistics
import ru.antonc.budget.data.entities.FullTransaction
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.data.entities.common.EventContent
import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import ru.antonc.budget.util.extenstions.default
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
        MutableLiveData<Long>().default(Calendar.getInstance().timeInMillis)
    val dateRangeValue: LiveData<String> = _dateRangeValue.map { date ->
        return@map SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)
    }

    private val _datePickerEvent = MutableLiveData<EventContent<Long>>()
    val datePickerEvent: LiveData<EventContent<Long>> = _datePickerEvent

    init {
        transactionRepository.getAllTransactions()
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
        _dateRangeValue.value?.let { date ->
            _datePickerEvent.value = EventContent(date)
        }
    }

    fun setDate(date: Long) {
        _dateRangeValue.postValue(date)
    }
}