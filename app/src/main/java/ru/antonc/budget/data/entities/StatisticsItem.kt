package ru.antonc.budget.data.entities

import com.github.mikephil.charting.data.PieEntry

data class StatisticsItem(
    val id: Long,
    val name: String,
    val sum: Double,
    val percent: Double
) {

    fun convertToPieEntry(): PieEntry {
        return PieEntry(
            percent.toFloat(),
            name
        )
    }
}