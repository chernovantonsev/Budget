package ru.antonc.budget.ui.transaction.selectaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import ru.antonc.budget.databinding.FragmentSelectAccountBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class SelectAccountFragment : BaseFragment(), Injectable {

    private lateinit var viewModel: SelectAccountViewModel

    var binding by autoCleared<FragmentSelectAccountBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SelectAccountViewModel::class.java)

        binding = FragmentSelectAccountBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@SelectAccountFragment
            }


        val selectAccountAdapter = SelectAccountAdapter { account ->
            findNavController().navigate(SelectAccountFragmentDirections.actionSelectAccountFragmentToEditSumFragment())
        }
        binding.accountsList.adapter = selectAccountAdapter
        viewModel.accountsList.observe(viewLifecycleOwner) { accounts ->
            selectAccountAdapter.submitList(accounts)
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.apply {
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }
}