package ru.antonc.budget.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import ru.antonc.budget.databinding.FragmentStatisticsBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment

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

        return binding.root
    }

}