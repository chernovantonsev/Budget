package ru.antonc.budget.util.extenstions

import java.util.*

fun Calendar.getDayToCompare(date: Long? = null): Int {
    date?.let {
        timeInMillis = date
    }

    return get(Calendar.YEAR) * 1000 + get(Calendar.DAY_OF_YEAR)
}
