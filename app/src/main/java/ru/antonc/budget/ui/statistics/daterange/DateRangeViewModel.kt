package ru.antonc.budget.ui.statistics.daterange

import ru.antonc.budget.repository.StatisticsRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject

class DateRangeViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : BaseViewModel() {

    fun saveChanges() {
        statisticsRepository.copyCacheToMain()
    }
}