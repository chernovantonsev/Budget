package ru.antonc.budget.ui.accounts.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import ru.antonc.budget.databinding.FragmentSummaryAccountsBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class SummaryAccountsFragment : BaseFragment(), Injectable {

    private lateinit var viewModel: SummaryAccountsViewModel

    var binding by autoCleared<FragmentSummaryAccountsBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SummaryAccountsViewModel::class.java)

        binding = FragmentSummaryAccountsBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@SummaryAccountsFragment.viewModel
                lifecycleOwner = this@SummaryAccountsFragment
            }



        return binding.root
    }

}