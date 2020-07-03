package ru.antonc.budget.ui.menu.categories.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.databinding.FragmentListOfCategoriesMenuBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

class CategoriesListFragment : BaseFragment(), Injectable {

    companion object {
        const val TRANSACTION_TYPE = "transaction_type"
    }

    private lateinit var viewModel: CategoriesListViewModel

    var binding by autoCleared<FragmentListOfCategoriesMenuBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CategoriesListViewModel::class.java)

        arguments?.let {
            it.getString(TRANSACTION_TYPE)?.let { transactionTypeValue ->
                TransactionType.fromValue(transactionTypeValue)?.let { transactionType ->
                    viewModel.setTransactionType(transactionType)
                }
            }
        }

        binding = FragmentListOfCategoriesMenuBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@CategoriesListFragment
            }

        val adapter = CategoriesListAdapter { category ->

        }
        binding.categoriesList.adapter = adapter

        viewModel.categoriesList.observe(viewLifecycleOwner) { categories ->
            adapter.submitList(categories)
        }

        return binding.root
    }
}