package ru.antonc.budget.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.antonc.budget.util.extenstions.getDayToCompare
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatisticsRepository @Inject constructor() {

    private val _dateRangeValue = MutableLiveData<Pair<Long, Long>>()
    val dateRangeValue: LiveData<Pair<Long, Long>> = _dateRangeValue

    private val _dateRangeValueCache = MutableLiveData<Pair<Long, Long>>()
    val dateRangeValueCache: LiveData<Pair<Long, Long>> = _dateRangeValueCache

    val dateRangeValueString: LiveData<String> = dateRangeValue
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
        _dateRangeValue.value = dateStart to dateEnd

        setDateToCache(dateStart, dateEnd)
    }

    fun setDateToCache(dateStart: Long, dateEnd: Long) {
        _dateRangeValueCache.value = dateStart to dateEnd
    }

    fun copyCacheToMain() {
        _dateRangeValueCache.value?.let { (dateStart, dateEnd) ->
            _dateRangeValue.value = dateStart to dateEnd
        }
    }
}