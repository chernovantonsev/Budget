package ru.antonc.budget.ui.statistics.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import ru.antonc.budget.databinding.FragmentSummaryStatisticsBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment

class SummaryStatisticsFragment : BaseFragment(false), Injectable {

    private lateinit var viewModel: SummaryStatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(SummaryStatisticsViewModel::class.java)

        val binding = FragmentSummaryStatisticsBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@SummaryStatisticsFragment.viewModel
                lifecycleOwner = this@SummaryStatisticsFragment
            }


        return binding.root
    }
}