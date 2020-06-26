package ru.antonc.budget.ui.statistics.daterange.day

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import ru.antonc.budget.repository.StatisticsRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class DayRangeViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : BaseViewModel() {

    val dateInitValue: LiveData<Long> =
        LiveDataReactiveStreams.fromPublisher(
            statisticsRepository.dataRangeValue
                .filter { (start, end) -> start == end }
                .map { (start, _) -> start }
                .distinctUntilChanged()
        )

    fun setDate(timeInMillis: Long) {
        statisticsRepository.setDateToCache(timeInMillis, timeInMillis)
    }
}