package ru.antonc.budget.ui.transaction

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.databinding.FragmentTransactionBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared

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

        when {
            params.id.isNotEmpty() -> getString(R.string.title_edit)
            params.type == TransactionType.INCOME.type -> getString(R.string.title_income)
            else -> getString(R.string.title_expense)
        }.also { title ->
            binding.toolbar.title = title
        }

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_transaction, menu)
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                viewModel.saveTransaction()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}