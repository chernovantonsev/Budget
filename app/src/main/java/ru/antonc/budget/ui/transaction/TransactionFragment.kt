package ru.antonc.budget.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.databinding.FragmentTransactionBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared
import ru.antonc.budget.util.extenstions.afterTextChanged

class TransactionFragment : BaseFragment(), Injectable, Toolbar.OnMenuItemClickListener {

    private lateinit var viewModel: TransactionViewModel

    private val params by navArgs<TransactionFragmentArgs>()

    var binding by autoCleared<FragmentTransactionBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TransactionViewModel::class.java)

        binding = FragmentTransactionBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@TransactionFragment.viewModel
                lifecycleOwner = this@TransactionFragment
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.setTransactionDetails(params.id, params.type)

        binding.etSum.afterTextChanged { sum -> viewModel.setSum(sum) }

        binding.toolbar.apply {
            inflateMenu(R.menu.menu_transaction)
            setOnMenuItemClickListener(this@TransactionFragment)
            setNavigationOnClickListener { requireActivity().onBackPressed() }

            when {
                params.id >= 0 -> getString(R.string.title_edit)
                params.type == TransactionType.INCOME.type -> getString(R.string.title_income)
                else -> getString(R.string.title_expense)
            }.also { transactionType ->
                title = transactionType
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                viewModel.saveTransaction()
                findNavController().navigateUp()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}