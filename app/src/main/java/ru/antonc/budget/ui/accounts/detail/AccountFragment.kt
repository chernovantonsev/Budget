package ru.antonc.budget.ui.accounts.detail

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
import ru.antonc.budget.databinding.FragmentAccountBinding
import ru.antonc.budget.di.Injectable
import ru.antonc.budget.ui.base.BaseFragment
import ru.antonc.budget.util.autoCleared
import ru.antonc.budget.util.extenstions.afterTextChanged
import ru.antonc.budget.util.extenstions.hideKeyboard

class AccountFragment : BaseFragment(), Injectable, Toolbar.OnMenuItemClickListener {

    private lateinit var viewModel: AccountViewModel

    var binding by autoCleared<FragmentAccountBinding>()

    private val params by navArgs<AccountFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AccountViewModel::class.java)

        viewModel.setAccountId(params.id)

        binding = FragmentAccountBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@AccountFragment.viewModel
                lifecycleOwner = this@AccountFragment
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.etBalance.afterTextChanged { sum -> viewModel.setBalance(sum) }

        binding.toolbar.apply {
            inflateMenu(R.menu.menu_account)
            setOnMenuItemClickListener(this@AccountFragment)

            title = if (params.id > 0L)
                getString(R.string.title_account_info)
            else
                getString(R.string.title_create_account)

            setNavigationOnClickListener {
                hideKeyboard()
                findNavController().navigateUp()
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                view?.hideKeyboard()
                viewModel.saveAccount()
                requireActivity().onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}