package ru.antonc.budget.ui.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.lifecycle.ViewModelProviders
import ru.antonc.budget.databinding.FragmentListOfAccountsBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class AccountsFragment : BaseFragment(), Injectable {

    private lateinit var viewModel: AccountsViewModel

    var binding by autoCleared<FragmentListOfAccountsBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AccountsViewModel::class.java)

        binding = FragmentListOfAccountsBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@AccountsFragment.viewModel
                lifecycleOwner = this@AccountsFragment
            }

        val adapter = AccountsAdapter { account ->

        }
        binding.accountsList.adapter = adapter

        viewModel.accountsList.observe(viewLifecycleOwner) { accounts ->
            adapter.submitList(accounts)
        }

        binding.fabAdd.setOnClickListener { createNewAccount() }

        return binding.root
    }

    private fun createNewAccount() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.apply {
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }
}