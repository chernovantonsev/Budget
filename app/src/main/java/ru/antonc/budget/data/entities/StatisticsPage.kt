package ru.antonc.budget.data.entities


interface StatisticsPage {
    enum class Type(val title: String) {
        INCOME("Доходы"), EXPENSE("Расходы"), SUMMARY("Сводка")
    }

    fun getTypeStatistics(): Type

    override fun equals(other: Any?): Boolean
}

interface IncomeExpensePage : StatisticsPage {
    val totalSum: String
    val itemsLegend: List<StatisticsItem>
}

data class IncomeStatisticsPage(
    override val totalSum: String,
    override val itemsLegend: List<StatisticsItem>
) : IncomeExpensePage {
    override fun getTypeStatistics(): StatisticsPage.Type = StatisticsPage.Type.INCOME
}

data class ExpenseStatisticsPage(
    override val totalSum: String,
    override val itemsLegend: List<StatisticsItem>
) : IncomeExpensePage {
    override fun getTypeStatistics(): StatisticsPage.Type = StatisticsPage.Type.EXPENSE
}

data class SummaryStatisticsPage(
    val incomes: Double,
    val expenses: Double,
    val balance: Double

) : StatisticsPage {
    override fun getTypeStatistics(): StatisticsPage.Type = StatisticsPage.Type.SUMMARY
}
