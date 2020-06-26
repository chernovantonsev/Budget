package ru.antonc.budget.ui.statistics

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.antonc.budget.data.entities.StatisticsPage
import ru.antonc.budget.ui.statistics.piechart.PieChartStatisticsFragment
import ru.antonc.budget.ui.statistics.piechart.PieChartStatisticsFragment.Companion.DATA_TYPE
import ru.antonc.budget.ui.statistics.summary.SummaryStatisticsFragment

class StatisticsViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val pages = ArrayList<StatisticsPage>()

    init {
        pages.addAll(StatisticsPage.values())
    }

    override fun getItemCount() = StatisticsPage.values().size

    override fun createFragment(position: Int): Fragment {
        return when (pages[position]) {
            StatisticsPage.EXPENSE,
            StatisticsPage.INCOME -> {
                PieChartStatisticsFragment().apply {
                    arguments = bundleOf(
                        DATA_TYPE to pages[position].name
                    )
                }
            }
            StatisticsPage.SUMMARY -> {
                SummaryStatisticsFragment()
            }
        }
    }
}

