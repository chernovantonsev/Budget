package ru.antonc.budget.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import ru.antonc.budget.repository.StatisticsRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : BaseViewModel() {

    val dateRangeValue: LiveData<String> =
        LiveDataReactiveStreams.fromPublisher(statisticsRepository.dateRangeValueString)

    init {
//        with(Calendar.getInstance().timeInMillis) {
//            statisticsRepository.setDateRange(this, this)
//        }
        statisticsRepository.setDateRange(1593701736131, 1594127260826)
    }
}