package ru.antonc.budget.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.google.android.material.tabs.TabLayoutMediator
import ru.antonc.budget.databinding.FragmentStatisticsBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.ui.date.DatePickerFragment

class StatisticsFragment : BaseFragment(), Injectable {

    private lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(StatisticsViewModel::class.java)

        val binding = FragmentStatisticsBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@StatisticsFragment.viewModel
                lifecycleOwner = this@StatisticsFragment
            }

        val pagerAdapter = ViewPagerAdapter()
        binding.vpInfo.adapter = pagerAdapter

        viewModel.pages.observe(viewLifecycleOwner) { pages ->
            pagerAdapter.setPagesInfo(pages)

            binding.tlTypes.removeAllTabs()

            TabLayoutMediator(binding.tlTypes, binding.vpInfo,
                TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                    pages[position].also { (tabName, _) ->
                        tab.text = tabName
                        tab.tag = tabName
                    }
                }).attach()
        }


        viewModel.datePickerEvent.observe(viewLifecycleOwner) { datePickEvent ->
            datePickEvent.getContentIfNotHandled()?.let { initDate ->
                DatePickerFragment.createDatePickerDialog(
                    initDate = initDate,
                    onDateSelected = { date -> viewModel.setDate(date) })
                    .let { datePickerFragment ->
                        if (isAdded)
                            datePickerFragment.show(
                                parentFragmentManager,
                                datePickerFragment.javaClass.name
                            )
                    }
            }
        }

        return binding.root
    }

}