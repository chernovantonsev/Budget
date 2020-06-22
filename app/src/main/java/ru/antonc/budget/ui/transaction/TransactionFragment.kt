package ru.antonc.budget.ui.transaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.antonc.budget.R
import ru.antonc.budget.data.entities.TransactionType
import ru.antonc.budget.databinding.FragmentTransactionBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.ui.date.DatePickerFragment
import ru.antonc.budget.util.autoCleared
import ru.antonc.budget.util.extenstions.afterTextChanged
import ru.antonc.budget.util.extenstions.hideKeyboard
import java.util.*

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
        viewModel.setTransactionDetails(params.id, params.type)

        binding = FragmentTransactionBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@TransactionFragment.viewModel
                lifecycleOwner = this@TransactionFragment
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.etSum.afterTextChanged { sum -> viewModel.setSum(sum) }

        binding.toolbar.apply {
            inflateMenu(R.menu.menu_transaction)
            setOnMenuItemClickListener(this@TransactionFragment)
            setNavigationOnClickListener { requireActivity().onBackPressed() }

            when {
                params.id.isNotEmpty() -> getString(R.string.title_edit)
                params.type == TransactionType.INCOME.type -> getString(R.string.title_income)
                else -> getString(R.string.title_expense)
            }.also { transactionType ->
                title = transactionType
            }
        }

        viewModel.datePickerEvent.observe(viewLifecycleOwner) { datePickEvent ->
            datePickEvent.getContentIfNotHandled()?.let { initDate ->
                showDatePickerDialog(
                    initDate = initDate,
                    onDateSelected = { date -> viewModel.setDate(date) })
            }
        }

        viewModel.navigateToCategoriesEvent.observe(viewLifecycleOwner) { navigateToCategoriesEvent ->
            navigateToCategoriesEvent.getContentIfNotHandled()?.let { id ->
                view.hideKeyboard()

                findNavController().navigate(
                    TransactionFragmentDirections.actionTransactionFragmentToCategoriesFragment(id)
                )
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                viewModel.saveTransaction(true)
                requireActivity().onBackPressed()
                true
            }
            R.id.action_remove -> {
                viewModel.removeTransaction()
                requireActivity().onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDatePickerDialog(
        title: String = "",
        initDate: Long,
        onDateSelected: (res: Long) -> Unit
    ) {
        DatePickerFragment().also { datePickerFragment ->
            Bundle().apply {
                putLong(DatePickerFragment.DATE, initDate)
                putString(DatePickerFragment.TITLE, title)
            }.let {
                datePickerFragment.arguments = it
            }

            datePickerFragment.setOnDateSetListener(DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
                    .let {
                        onDateSelected.invoke(it.timeInMillis)
                    }
            })
        }.let { datePickerFragment ->
            if (isAdded)
                datePickerFragment.show(parentFragmentManager, datePickerFragment.javaClass.name)
        }

    }
}