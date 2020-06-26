package ru.antonc.budget.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import ru.antonc.budget.data.entities.common.EventContent
import ru.antonc.budget.repository.StatisticsRepository
import ru.antonc.budget.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : BaseViewModel() {

    val dateRangeValue: LiveData<String> =
        LiveDataReactiveStreams.fromPublisher(statisticsRepository.dateRangeValueString)


    private val _datePickerEvent = MutableLiveData<EventContent<Long>>()
    val datePickerEvent: LiveData<EventContent<Long>> = _datePickerEvent

    init {
        with(Calendar.getInstance().timeInMillis) {
            statisticsRepository.setDateRange(this, this)
        }
    }


    fun selectDateRange() {
        statisticsRepository.dataRangeValue
            .blockingFirst()?.let { (start, _) ->
                _datePickerEvent.value = EventContent(start)
            }
    }

    fun setDate(date: Long) {
        statisticsRepository.setDateRange(date, date)
    }

    fun setDate(dateStart: Long, dateEnd: Long) {
        statisticsRepository.setDateRange(dateStart, dateEnd)
    }


}