package ru.antonc.budget.ui.statistics.daterange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayoutMediator
import ru.antonc.budget.R
import ru.antonc.budget.databinding.FragmentDateRangeBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class DateRangeFragment : BaseFragment(), Injectable {

    private lateinit var viewModel: DateRangeViewModel

    var binding by autoCleared<FragmentDateRangeBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DateRangeViewModel::class.java)

        binding = FragmentDateRangeBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@DateRangeFragment
            }

        val pagerAdapter = DateRangeViewPagerAdapter(childFragmentManager, lifecycle)
        binding.vpInfo.adapter = pagerAdapter

        DateRangeViewPagerAdapter.DateRangePage.values().let { pages ->
            binding.tabs.removeAllTabs()

            TabLayoutMediator(binding.tabs, binding.vpInfo,
                TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                    pages[position].also { page ->
                        tab.text = page.title
                        tab.tag = page.title
                    }
                }).attach()
        }

        binding.buttonOk.setOnClickListener {
            viewModel.saveChanges()
            onBackPressed()
        }

        return binding.root
    }

    override fun onBackPressed(): Boolean {
        if (isAdded)
            parentFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    0,
                    R.anim.slide_out_bottom,
                    0,
                    0
                )
                .detach(this)
                .commitNow()

        return true
    }
}