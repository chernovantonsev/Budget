package ru.antonc.budget.ui.statistics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayoutMediator
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.StatisticsPage
import ru.antonc.budget.databinding.FragmentStatisticsBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.ui.statistics.daterange.DateRangeFragment
import ru.antonc.budget.util.autoCleared

class StatisticsFragment : BaseFragment(), Injectable {

    private lateinit var viewModel: StatisticsViewModel

    var binding by autoCleared<FragmentStatisticsBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(StatisticsViewModel::class.java)

        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@StatisticsFragment.viewModel
                lifecycleOwner = this@StatisticsFragment

                onDateRangeClick = View.OnClickListener {
                    openDateRange()
                }
            }

        val pagerAdapter = StatisticsViewPagerAdapter(childFragmentManager, lifecycle)
        binding.vpInfo.adapter = pagerAdapter

        StatisticsPage.values().let { pages ->
            binding.tlTypes.removeAllTabs()

            TabLayoutMediator(binding.tlTypes, binding.vpInfo,
                TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                    pages[position].also { page ->
                        tab.text = page.title
                        tab.tag = page.title
                    }
                }).attach()
        }

        return binding.root
    }

    private fun openDateRange() {
        binding.fragmentContainer.visibility = View.VISIBLE

        removeBackPressedListener()

        childFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_top,
                0,
               0,
                0
            )
            .add(R.id.fragment_container, DateRangeFragment())
            .commit()
    }

}