package ru.antonc.budget.data.entities

data class CategoryStatistics (
    val id: Long,
    val name: String,
    val sum: Double,
    val percent: Double,
    val color: Int = 0
)