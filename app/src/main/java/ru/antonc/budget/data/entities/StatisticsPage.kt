package ru.antonc.budget.data.entities

import java.util.Collections.emptyList

data class StatisticsPage(
    val type: Type,
    val totalSum: String = "",
    val itemsLegend: List<StatisticsItem> = emptyList()
) {
    enum class Type(val title: String) {
        INCOME("Доходы"), EXPENSE("Расходы"), SUMMARY("Сводка")
    }
}

