package ru.antonc.budget.ui.statistics.daterange

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.antonc.budget.ui.statistics.daterange.any.AnyRangeFragment
import ru.antonc.budget.ui.statistics.daterange.day.DayRangeFragment

class DateRangeViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    enum class DateRangePage(val title: String) {
        DAY("День"), ANY("Период")
    }


    private val pages = ArrayList<DateRangePage>()

    init {
        pages.addAll(DateRangePage.values())
    }

    override fun getItemCount() = DateRangePage.values().size

    override fun createFragment(position: Int): Fragment {
        return when (pages[position]) {
            DateRangePage.DAY -> {
                DayRangeFragment()
            }
            DateRangePage.ANY -> {
                AnyRangeFragment()
            }
        }
    }
}

