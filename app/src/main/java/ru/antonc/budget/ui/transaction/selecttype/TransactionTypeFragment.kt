package ru.antonc.budget.ui.transaction.selecttype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.antonc.budget.databinding.FragmentSelectTypeTransactionBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class TransactionTypeFragment : BaseFragment(), Injectable {

    var binding by autoCleared<FragmentSelectTypeTransactionBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSelectTypeTransactionBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@TransactionTypeFragment
            }

        binding.transactionTypesList.adapter = TransactionTypeAdapter { transactionType ->
            findNavController().navigate(
                TransactionTypeFragmentDirections.actionTransactionTypeFragmentToSelectAccountFragment(
                    transactionType.name
                )
            )
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.apply {
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }
}