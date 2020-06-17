package ru.antonc.budget.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import ru.antonc.budget.databinding.FragmentOverviewBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment

class OverviewFragment : BaseFragment(), Injectable {

    private lateinit var viewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(OverviewViewModel::class.java)

        val binding = FragmentOverviewBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@OverviewFragment.viewModel
                lifecycleOwner = this@OverviewFragment
            }

        return binding.root
    }

}