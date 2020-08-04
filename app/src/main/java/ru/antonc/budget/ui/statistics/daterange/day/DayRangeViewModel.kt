package ru.antonc.budget.ui.statistics.daterange.day

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.antonc.budget.repository.StatisticsRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class DayRangeViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : BaseViewModel() {

    val dateInitValue: LiveData<Long> = statisticsRepository.dateRangeValueL.map { (start, end) ->
        if (start == end)
            return@map start
        else -1L
    }

    fun setDate(timeInMillis: Long) {
        statisticsRepository.setDateToCache(timeInMillis, timeInMillis)
    }
}