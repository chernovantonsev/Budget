package ru.antonc.budget.ui.transaction.selectaccount

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.antonc.budget.databinding.FragmentSelectAccountBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class SelectAccountFragment : BaseFragment(), Injectable {

    private lateinit var viewModel: SelectAccountViewModel

    var binding by autoCleared<FragmentSelectAccountBinding>()

    private val params by navArgs<SelectAccountFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SelectAccountViewModel::class.java)
        viewModel.setTransactionInfo(params.transactionType)

        binding = FragmentSelectAccountBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@SelectAccountFragment
            }

        val selectAccountAdapter = SelectAccountAdapter { account ->
            viewModel.selectAccount(account)
        }

        binding.accountsList.adapter = selectAccountAdapter
        viewModel.accountsList.observe(viewLifecycleOwner) { accounts ->
            selectAccountAdapter.submitList(accounts)
        }

        viewModel.navigateToEditSumEvent.observe(viewLifecycleOwner) { navigateToEditSumEvent ->
            navigateToEditSumEvent.getContentIfNotHandled()?.let {
                findNavController().navigate(SelectAccountFragmentDirections.actionSelectAccountFragmentToEditSumFragment())
            }
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.apply {
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }
}