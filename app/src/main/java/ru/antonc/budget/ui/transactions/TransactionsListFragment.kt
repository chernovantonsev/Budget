package ru.antonc.budget.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import ru.antonc.budget.databinding.FragmentListOfTransactionsBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class TransactionsListFragment : BaseFragment(), Injectable, Toolbar.OnMenuItemClickListener {

    private lateinit var viewModel: TransactionsListViewModel

    var binding by autoCleared<FragmentListOfTransactionsBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TransactionsListViewModel::class.java)

        binding = FragmentListOfTransactionsBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@TransactionsListFragment.viewModel
                lifecycleOwner = this@TransactionsListFragment
            }

        val adapter = TransactionsListAdapter()
        binding.transactionsList.adapter = adapter

        viewModel.transactionsList.observe(viewLifecycleOwner) { transactions ->
            adapter.submitList(transactions)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.apply {
            setOnMenuItemClickListener(this@TransactionsListFragment)
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }
}