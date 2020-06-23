package ru.antonc.budget.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.antonc.budget.data.entities.common.EventContent
import ru.antonc.budget.ui.base.BaseViewModel
import ru.antonc.budget.util.extenstions.default
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class StatisticsViewModel @Inject constructor() : BaseViewModel() {

    private val _types = MutableLiveData<List<String>>()
    val types: LiveData<List<String>> = _types

    private val _dateRangeValue =
        MutableLiveData<Long>().default(Calendar.getInstance().timeInMillis)
    val dateRangeValue: LiveData<String> = _dateRangeValue.map { date ->
        return@map SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)
    }

    private val _datePickerEvent = MutableLiveData<EventContent<Long>>()
    val datePickerEvent: LiveData<EventContent<Long>> = _datePickerEvent

    init {
        ArrayList<String>().apply {
            add("Расходы")
            add("Доходы")
        }.let { _types.postValue(it) }
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