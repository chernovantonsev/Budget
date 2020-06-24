package ru.antonc.budget.data.entities

data class StatisticsPage(
    val name: String = "",
    val totalSum: String = "",
    val itemsLegend: List<StatisticsItem> = emptyList()
)