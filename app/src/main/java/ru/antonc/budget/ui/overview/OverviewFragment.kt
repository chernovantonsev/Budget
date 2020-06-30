package ru.antonc.budget.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ru.antonc.budget.R
import ru.antonc.budget.databinding.FragmentOverviewBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.ui.main.MainActivity

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

        val accountsAdapter = AccountsAdapter { account ->
            OverviewFragmentDirections.actionOverviewFragmentToAccountFragment().apply {
                id = account.id
            }.let { findNavController().navigate(it) }
        }
        binding.accountsList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.accountsList.adapter = accountsAdapter
        viewModel.accountsList.observe(viewLifecycleOwner) { accounts ->
            accountsAdapter.submitList(accounts)
        }

        binding.itemLastTransactions.buttonShowMore.setOnClickListener {
            if (requireActivity() is MainActivity)
                (requireActivity() as MainActivity).setSelectedItemNavigation(R.id.navigation_transactions)
        }

        val transactionsAdapter = TransactionsListAdapter()
        binding.itemLastTransactions.transactionsList.adapter = transactionsAdapter

        viewModel.transactionsList.observe(viewLifecycleOwner) { transactions ->
            transactionsAdapter.submitList(transactions)
        }

        return binding.root
    }

}