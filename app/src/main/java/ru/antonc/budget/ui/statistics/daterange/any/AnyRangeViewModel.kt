package ru.antonc.budget.ui.statistics.daterange.any

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.antonc.budget.data.entities.common.EventContent
import ru.antonc.budget.repository.StatisticsRepository
import ru.antonc.budget.ui.base.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class AnyRangeViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : BaseViewModel() {

    companion object {
        const val START_DATE = 0
        const val END_DATE = 1
    }

    private val _datePickerEvent = MutableLiveData<EventContent<Pair<Long, Int>>>()
    val datePickerEvent: LiveData<EventContent<Pair<Long, Int>>> = _datePickerEvent

    val dateStart: LiveData<String> =
        LiveDataReactiveStreams.fromPublisher(statisticsRepository.dataRangeValueCache)
            .map { (start, _) ->
                formatDate(start)
            }

    val dateEnd: LiveData<String> =
        LiveDataReactiveStreams.fromPublisher(statisticsRepository.dataRangeValueCache)
            .map { (_, end) ->
                formatDate(end)
            }


    fun changeDateStart() {
        changeDate(START_DATE)
    }


    fun changeDateEnd() {
        changeDate(END_DATE)
    }

    private fun changeDate(kind: Int) {
        statisticsRepository.dataRangeValueCache
            .blockingFirst()?.let { (_, end) ->
                _datePickerEvent.value = EventContent(end to kind)
            }
    }

    private fun formatDate(date: Long) =
        SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)

    fun setDate(date: Long, kind: Int) {
        statisticsRepository.dataRangeValueCache
            .blockingFirst()?.let { (start, end) ->
                when (kind) {
                    START_DATE -> {
                        statisticsRepository.setDateToCache(date, end)
                    }
                    END_DATE -> {
                        statisticsRepository.setDateToCache(start, date)
                    }
                }
            }
    }

}