package ru.antonc.budget.ui.accounts.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ru.antonc.budget.databinding.FragmentListOfAccountsBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.accounts.AccountsFragmentDirections
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class AccountsListFragment : BaseFragment(), Injectable {

    companion object {
        const val IS_PAGE = "is_page"
    }

    private lateinit var viewModel: AccountsListViewModel

    private var isPage: Boolean = false

    var binding by autoCleared<FragmentListOfAccountsBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AccountsListViewModel::class.java)

        binding = FragmentListOfAccountsBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@AccountsListFragment.viewModel
                lifecycleOwner = this@AccountsListFragment
            }

        val adapter =
            AccountsAdapter { account ->
                AccountsFragmentDirections.actionAccountsFragmentToAccountFragment()
                    .apply {
                        id = account.id
                    }.let { findNavController().navigate(it) }
            }

        arguments?.let {
            it.getBoolean(IS_PAGE).let { isPage ->
                this@AccountsListFragment.isPage = isPage
            }
        }

        binding.accountsList.adapter = adapter
        viewModel.accountsList.observe(viewLifecycleOwner) { accounts ->
            adapter.submitList(accounts)
        }

        binding.buttonAddAccount.setOnClickListener {
            findNavController().navigate(
                AccountsFragmentDirections.actionAccountsFragmentToAccountFragment()
            )
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.apply {
            if (isPage)
                this.visibility = View.GONE
            else {
                this.visibility = View.VISIBLE
                setNavigationOnClickListener { findNavController().navigateUp() }
            }
        }
    }
}