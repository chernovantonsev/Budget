package ru.antonc.budget.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import ru.antonc.budget.util.extenstions.getDayToCompare
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatisticsRepository @Inject constructor() {

    private val _dateRangeValueL = MutableLiveData<Pair<Long, Long>>()
    val dateRangeValueL: LiveData<Pair<Long, Long>> = _dateRangeValueL

    private val _dateRangeValueCacheL = MutableLiveData<Pair<Long, Long>>()
    val dateRangeValueCacheL: LiveData<Pair<Long, Long>> = _dateRangeValueCacheL


    private val _dateRangeValue = BehaviorRelay.create<Pair<Long, Long>>()
    val dateRangeValue: Flowable<Pair<Long, Long>> =
        _dateRangeValue.toFlowable(BackpressureStrategy.LATEST)

    private val _dateRangeValueCache = BehaviorRelay.create<Pair<Long, Long>>()
    val dataRangeValueCache: Flowable<Pair<Long, Long>> =
        _dateRangeValueCache.toFlowable(BackpressureStrategy.LATEST)

    val dateRangeValueString: LiveData<String> = dateRangeValueL
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

        _dateRangeValueL.value = dateStart to dateEnd

        setDateToCache(dateStart, dateEnd)
    }

    fun setDateToCache(dateStart: Long, dateEnd: Long) {
        _dateRangeValueCache.accept(dateStart to dateEnd)
        _dateRangeValueCacheL.value = dateStart to dateEnd

    }

    fun copyCacheToMain() {
        _dateRangeValueCache.value?.let { (dateStart, dateEnd) ->
            _dateRangeValue.accept(dateStart to dateEnd)
        }

        _dateRangeValueCacheL.value?.let { (dateStart, dateEnd) ->
            _dateRangeValueL.value = dateStart to dateEnd
        }
    }
}