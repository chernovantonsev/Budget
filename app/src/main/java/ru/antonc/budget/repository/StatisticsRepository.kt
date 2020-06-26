package ru.antonc.budget.repository

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import ru.antonc.budget.data.AppDatabase
import ru.antonc.budget.util.extenstions.getDayToCompare
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatisticsRepository @Inject constructor(
    private val database: AppDatabase,
    private val dataDisposable: CompositeDisposable
) {

    private val _dateRangeValue = BehaviorRelay.create<Pair<Long, Long>>()
    val dataRangeValue = _dateRangeValue.toFlowable(BackpressureStrategy.LATEST)

    val dateRangeValueString: Flowable<String> = dataRangeValue
        .map { (start, end) ->
            with(Calendar.getInstance()) {
                val today = getDayToCompare()
                val rangeIsDay = getDayToCompare(start) == getDayToCompare(end)

                return@map when {
                    rangeIsDay && today == getDayToCompare(start) -> "Сегодня"
                    rangeIsDay && (today - 1) == getDayToCompare(start) -> "Вчера"
                    rangeIsDay -> formatDate(start)
                    else -> "${formatDate(start)} - ${formatDate(end)}"
                }
            }
        }


    private fun formatDate(date: Long) =
        SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)

    fun setDateRange(dateStart: Long, dateEnd: Long) {
        _dateRangeValue.accept(dateStart to dateEnd)
    }
}